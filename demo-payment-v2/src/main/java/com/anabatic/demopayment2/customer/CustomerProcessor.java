package com.anabatic.demopayment2.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;

import com.anabatic.demopayment2.entity.Payment;
import com.anabatic.demopayment2.order.OrderSink;
import com.anabatic.demopayment2.order.UpdateOrderEvent;
import com.anabatic.demopayment2.orderaggregator.OrderAggregatorEvent;
import com.anabatic.demopayment2.orderaggregator.OrderAggregatorSink;
import com.anabatic.demopayment2.orderaggregator.SubjectType;
import com.anabatic.demopayment2.record.PaymentStatus;
import com.anabatic.demopayment2.service.PaymentService;
import com.ati.syd.messaging.produce.MessagingProduce;
import com.ati.syd.spring.jms.annotations.ComponentJms;

@ComponentJms
public class CustomerProcessor {
	private static final Logger LOG = LogManager.getLogger(CustomerProcessor.class);

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private CustomerSink customerSink;

	@Autowired
	private OrderSink orderSink;

	@Autowired
	private OrderAggregatorSink orderAggregatorSink;

	@Autowired
	private MessagingProduce<Object> messagingProduce;
	
	@KafkaListener(topics="transaction.response.customer.v2")
	public void apply(Message<CustomerResponseTransactionCommand> customerResponseTransactionCommand) {
		LOG.info(customerResponseTransactionCommand);
		CustomerResponseTransactionCommand payload = customerResponseTransactionCommand.getPayload();
		Payment payment = paymentService.findByPaymentId(payload.getPaymentId());
		PaymentStatus paymentStatus;
		if (payload.getMessage().contains("Successful")) {
			paymentStatus = PaymentStatus.SUCCESSFULL;
			Payment newPayment = paymentService.createNewPayment(payment.getCustomerId(), payment.getOrderId(),
					payload.getMessage(), payment.getTotalPayment(), paymentStatus);
			messagingProduce.produce("order.aggregator.update", new OrderAggregatorEvent(payment.getOrderId(), newPayment, SubjectType.PAYMENT));
			messagingProduce.produce("order.update.v2", new UpdateOrderEvent(payment.getOrderId(), PaymentStatus.PAID.toString(), null));
		} else {
			paymentStatus = PaymentStatus.FAILURE;
			Payment newPayment = paymentService.createNewPayment(payment.getCustomerId(), payment.getOrderId(),
					payload.getMessage(), payment.getTotalPayment(), paymentStatus);
			messagingProduce.produce("order.aggregator.update", new OrderAggregatorEvent(payment.getOrderId(), newPayment, SubjectType.PAYMENT));
			messagingProduce.produce("order.update.v2", new UpdateOrderEvent(payment.getOrderId(), PaymentStatus.FAILURE.toString(), payload.getMessage()));
		}
	}
}
