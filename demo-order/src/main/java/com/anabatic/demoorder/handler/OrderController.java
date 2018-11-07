package com.anabatic.demoorder.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anabatic.demoorder.command.ConfirmOrder;
import com.anabatic.demoorder.command.CreateOrder;
import com.anabatic.demoorder.entity.Order;
import com.anabatic.demoorder.events.FindCustomerEvent;
import com.anabatic.demoorder.events.FindProductEvent;
import com.anabatic.demoorder.events.OrderAggregatorEvent;
import com.anabatic.demoorder.events.OrderEvent;
import com.anabatic.demoorder.events.OrderEventType;
import com.anabatic.demoorder.events.OrderSyncRequest;
import com.anabatic.demoorder.events.SubjectType;
import com.anabatic.demoorder.repository.OrderRepository;
import com.anabatic.demoorder.service.CreateOrderService;
import com.anabatic.demoorder.service.OrderConfirmationService;
import com.anabatic.demoorder.sink.CustomerSink;
import com.anabatic.demoorder.sink.OrderAggregatorSink;
import com.anabatic.demoorder.sink.ProductSink;
import com.anabatic.demoorder.sink.StreamGateway;
import com.anabatic.demoorder.util.Utils;
import com.ati.syd.messaging.produce.MessagingProduce;

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
	private CustomerSink customerSink;
	@Autowired
	private OrderAggregatorSink orderAggregatorSink;
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
		
		messagingProduce.produceAsynhcronous("app.order.v1",new OrderEvent(order, OrderEventType.ORDER_CREATED));
		messagingProduce.produceAsynhcronous("customer.find.id.v1",new FindCustomerEvent(order.getCustomerId(), order.getOrderId(), null));
		messagingProduce.produceAsynhcronous("get.info.from.order.v1",new FindProductEvent(order.getProduct(), order.getOrderId()));
		
		byte [] retval = (byte[]) gateway.create(new OrderSyncRequest(order.getOrderId())).getPayload();
		Object obj = Utils.convertFromByteJsonToObject(retval);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj);
	}

	@PostMapping("/confirm-order")
	public ResponseEntity<?> confirmOrder(@RequestBody ConfirmOrder confirmOrder) {
		try {
			Order order = repository.findByOrderId(confirmOrder.getOrderId());
			
			messagingProduce.produceAsynhcronous("app.order.v1",new OrderEvent(order, OrderEventType.ORDER_CONFIRMED));
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
					.body("Confirmation accepted");
		} catch (Exception e2) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
					.body("OrderId not found");
		}
	}
}
