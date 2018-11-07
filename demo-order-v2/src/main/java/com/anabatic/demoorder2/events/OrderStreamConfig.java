package com.anabatic.demoorder2.events;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.anabatic.demoorder2.entity.Order;
import com.anabatic.demoorder2.product.UpdateQtyProductEvent;
import com.anabatic.demoorder2.record.OrderStatus;
import com.anabatic.demoorder2.service.UpdateOrderService;
import com.anabatic.demoorder2.sink.OrderAggregatorSink;
import com.anabatic.demoorder2.sink.OrderSink;
import com.anabatic.demoorder2.sink.ProductSink;
import com.ati.syd.messaging.produce.MessagingProduce;
import com.ati.syd.spring.jms.annotations.ComponentJms;

@ComponentJms
public class OrderStreamConfig {
	private static final Logger LOG = LogManager.getLogger(OrderStreamConfig.class);

	@Autowired
	private UpdateOrderService updateOrderService;

	@Autowired
	private OrderAggregatorSink orderAggregatorSink;
	
	@Autowired
	private ProductSink productSink;
	
	@Autowired
	private MessagingProduce<Object> messagingProduce;

	@KafkaListener(topics="order.update")
	public void apply(Message<UpdateOrderEvent> updateOrderEvent) {
		LOG.info("Getting update for Order ---- " + updateOrderEvent.getPayload().getOrderId() + " :: "
				+ updateOrderEvent.getPayload().getOrderStatus());
		if (updateOrderEvent.getPayload() != null) {
			Order updatedOrder = updateOrderService.updateOrder(updateOrderEvent.getPayload());
			
			messagingProduce.produceAsynhcronous("order.aggregator.update", new OrderAggregatorEvent(updatedOrder.getOrderId(), updatedOrder, SubjectType.ORDER));
			if (updatedOrder.getOrderStatus().equals(OrderStatus.PAID)) {
				messagingProduce.produceAsynhcronous("product.update.v1",new UpdateQtyProductEvent(
						updatedOrder.getProduct(), updatedOrder.getOrderId(),
						updatedOrder.getCustomerId(), updatedOrder.getQty().longValue(),
						updatedOrder.getProductPrice().multiply(BigDecimal.valueOf(updatedOrder.getQty())),
					    "v2"));
			}
		}
	}
}
