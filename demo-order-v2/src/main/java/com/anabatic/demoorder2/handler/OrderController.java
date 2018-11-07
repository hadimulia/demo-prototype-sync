package com.anabatic.demoorder2.handler;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anabatic.demoorder2.command.ConfirmOrder;
import com.anabatic.demoorder2.command.CreateOrder;
import com.anabatic.demoorder2.entity.Order;
import com.anabatic.demoorder2.events.FindCustomerEvent;
import com.anabatic.demoorder2.events.FindProductEvent;
import com.anabatic.demoorder2.events.OrderAggregatorEvent;
import com.anabatic.demoorder2.events.OrderEvent;
import com.anabatic.demoorder2.events.OrderEventType;
import com.anabatic.demoorder2.events.OrderSyncRequest;
import com.anabatic.demoorder2.events.SubjectType;
import com.anabatic.demoorder2.repository.OrderRepository;
import com.anabatic.demoorder2.service.CreateOrderService;
import com.anabatic.demoorder2.service.OrderConfirmationService;
import com.anabatic.demoorder2.sink.CustomerSink;
import com.anabatic.demoorder2.sink.OrderAggregatorSink;
import com.anabatic.demoorder2.sink.ProductSink;
import com.anabatic.demoorder2.sink.StreamGateway;
import com.anabatic.demoorder2.util.Utils;
import com.ati.syd.messaging.produce.MessagingProduce;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class OrderController {

	private Logger log = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderRepository repository;
	@Autowired
	private CreateOrderService createOrderService;
	@Autowired
	private OrderConfirmationService orderConfirmationService;
	@Autowired
	private Source source;
	@Autowired
	private OrderAggregatorSink orderAggregatorSink;
	@Autowired
	private CustomerSink customerSink;
	@Autowired
	private ProductSink productSink;
	@Autowired
	private StreamGateway gateway;
	
	@Autowired
	private MessagingProduce<Object> messagingProduce;
	

	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrder createOrder) {
		Order order = createOrderService.createOrder(createOrder);
		messagingProduce.produceAsynhcronous("order.aggregator.update", new OrderAggregatorEvent(order.getOrderId(), order, SubjectType.ORDER));
		messagingProduce.produceAsynhcronous("app.order.v2", new OrderEvent(order, OrderEventType.ORDER_CREATED));
		messagingProduce.produceAsynhcronous("customer.find.id.v2", new FindCustomerEvent(order.getCustomerId(), order.getOrderId(), null));
		messagingProduce.produceAsynhcronous("get.info.from.order.v2", new FindProductEvent(order.getProduct(), order.getOrderId()));
		
		byte [] retval = (byte[]) gateway.create(new OrderSyncRequest(order.getOrderId())).getPayload();
		Object obj = Utils.convertFromByteJsonToObject(retval);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj);
	}

	@PostMapping("/confirm-order")
	public ResponseEntity<?> confirmOrder(@RequestBody ConfirmOrder confirmOrder) {
		try {
			Order order = repository.findByOrderId(confirmOrder.getOrderId());
			messagingProduce.produceAsynhcronous("app.order.v2",new OrderEvent(order, OrderEventType.ORDER_CONFIRMED));
			byte [] retval = (byte[]) gateway.confirm(new OrderSyncRequest(order.getOrderId())).getPayload();
			Object obj = Utils.convertFromByteJsonToObject(retval);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj);
		} catch (Exception e2) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("OrderId not found");
		}
	}
	
	/*@PostMapping("/test-order")
	public ResponseEntity<?> testOrder(@RequestBody ConfirmOrder confirmOrder) {
		try {
			byte [] retval = (byte[]) gateway.process(confirmOrder).getPayload();
			Object obj = Utils.convertFromByteJsonToObject(retval);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj);
		} catch (Exception e2) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("OrderId not found");
		}
	}*/
}
