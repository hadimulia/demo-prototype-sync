package com.anabatic.demopayment2.order;

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

import com.anabatic.demopayment2.customer.CustomerSink;
import com.anabatic.demopayment2.customer.UpdateBalanceCustomerEvent;
import com.anabatic.demopayment2.entity.Payment;
import com.anabatic.demopayment2.orderaggregator.OrderAggregatorEvent;
import com.anabatic.demopayment2.orderaggregator.OrderAggregatorSink;
import com.anabatic.demopayment2.orderaggregator.SubjectType;
import com.anabatic.demopayment2.record.PaymentStatus;
import com.anabatic.demopayment2.service.PaymentService;
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
	private CustomerSink customerSink;

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
			messagingProduce.produce("order.update.v2",
					new UpdateOrderEvent(order.getOrderId(), "WAITING_PAYMENT", null));
			messagingProduce.produce("order.aggregator.update",
					new OrderAggregatorEvent(order.getOrderId(), newPayment, SubjectType.PAYMENT));
			break;
		case ORDER_CONFIRMED:
			Payment payment = paymentService.findByOrderId(order.getOrderId());
			if (order.getPaymentType().equalsIgnoreCase("WALLET")) {
				messagingProduce.produce("customer.update.balance.v2", new UpdateBalanceCustomerEvent(
						order.getCustomerId(), payment.getPaymentId(), totalBalance, "DEBIT"));
			} else {
				Payment confirmedPayment = paymentService.createNewPayment(order.getCustomerId(), order.getOrderId(),
						order.getProduct() + "(" + order.getQty() + ")", totalBalance, PaymentStatus.SUCCESSFULL);
				messagingProduce.produce("order.aggregator.update",
						new OrderAggregatorEvent(order.getOrderId(), confirmedPayment, SubjectType.PAYMENT));
				messagingProduce.produce("order.update.v2",
						new UpdateOrderEvent(order.getOrderId(), PaymentStatus.PAID.toString(), null));
			}
			break;
		default:
			break;
		}
	}
}
