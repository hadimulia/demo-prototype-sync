package com.anabatic.demopayment.order;

import java.math.BigDecimal;

import javax.jms.MessageProducer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.anabatic.demopayment.entity.Payment;
import com.anabatic.demopayment.orderaggregator.OrderAggregatorEvent;
import com.anabatic.demopayment.orderaggregator.OrderAggregatorSink;
import com.anabatic.demopayment.orderaggregator.SubjectType;
import com.anabatic.demopayment.record.PaymentStatus;
import com.anabatic.demopayment.service.PaymentService;
import com.ati.syd.messaging.produce.MessagingProduce;
import com.ati.syd.spring.jms.annotations.ComponentJms;

@ComponentJms
public class OrderProccessor {

	private static final Logger LOG = LogManager.getLogger(OrderProccessor.class);

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderSink orderSink;

	@Autowired
	private OrderAggregatorSink orderAggregatorSink;

	@Autowired
	private MessagingProduce<Object> messagingProduce;

	@KafkaListener(topics = "app.order")
	public void apply(Message<OrderEvent> orderEvent) {
		LOG.info(orderEvent);
		Order order = orderEvent.getPayload().getSubject();
		BigDecimal totalBalance = order.getProductPrice().multiply(BigDecimal.valueOf(order.getQty()));
		switch (orderEvent.getPayload().getEventType()) {
		case ORDER_CREATED:
			Payment newPayment = paymentService.createNewPayment(order.getCustomerId(), order.getOrderId(),
					order.getProduct() + "(" + order.getQty() + ")", totalBalance, PaymentStatus.PENDING);
			messagingProduce.produceAsynhcronous("order.update",
					new UpdateOrderEvent(order.getOrderId(), "WAITING_PAYMENT", null));
			messagingProduce.produceAsynhcronous("order.aggregator.update",
					new OrderAggregatorEvent(newPayment.getOrderId(), newPayment, SubjectType.PAYMENT));
			break;
		case ORDER_CONFIRMED:
			Payment confirmedPayment = paymentService.createNewPayment(order.getCustomerId(), order.getOrderId(),
					order.getProduct() + "(" + order.getQty() + ")", totalBalance, PaymentStatus.SUCCESSFULL);
			messagingProduce.produceAsynhcronous("order.aggregator.update",
					new OrderAggregatorEvent(order.getOrderId(), confirmedPayment, SubjectType.PAYMENT));
			messagingProduce.produceAsynhcronous("order.update",
					new UpdateOrderEvent(order.getOrderId(), PaymentStatus.PAID.toString(), null));
			break;
		default:
			break;
		}
	}
}
