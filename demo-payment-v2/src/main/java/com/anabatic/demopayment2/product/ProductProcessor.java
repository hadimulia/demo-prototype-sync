package com.anabatic.demopayment2.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.anabatic.demopayment2.entity.Payment;
import com.anabatic.demopayment2.order.OrderProccessor;
import com.anabatic.demopayment2.orderaggregator.OrderAggregatorEvent;
import com.anabatic.demopayment2.orderaggregator.OrderAggregatorSink;
import com.anabatic.demopayment2.orderaggregator.SubjectType;
import com.anabatic.demopayment2.service.PaymentService;
import com.ati.syd.messaging.produce.MessagingProduce;
import com.ati.syd.spring.jms.annotations.ComponentJms;

import reactor.core.publisher.Mono;

@ComponentJms
public class ProductProcessor {

	private static final Logger LOG = LogManager.getLogger(OrderProccessor.class);

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private OrderAggregatorSink orderAggregatorSink;

	@Autowired
	private MessagingProduce<Object> messagingProduce;

	@KafkaListener(topics = "update.payment")
	public void apply(Message<UpdatePaymentEvent> updateMessage) {
		LOG.info(updateMessage);
		UpdatePaymentEvent UpdatePaymentEvent = updateMessage.getPayload();
		Payment payment = paymentService.findByOrderId(UpdatePaymentEvent.getOrderId());
		payment.setPaymentStatus(UpdatePaymentEvent.getStatus());
		payment.setDescription(UpdatePaymentEvent.getDesc());
		Payment updatedPayment = paymentService.updatePayment(payment);
		messagingProduce.produce("order.aggregator.update",
				new OrderAggregatorEvent(updatedPayment.getOrderId(), updatedPayment, SubjectType.PAYMENT));
	}

}
