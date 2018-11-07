package com.anabatic.demopayment2.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anabatic.demopayment2.entity.Payment;
import com.anabatic.demopayment2.record.PaymentStatus;
import com.anabatic.demopayment2.repository.PaymentRepository;
import com.anabatic.demopayment2.service.PaymentService;
@Service
public class PaymentServiceImpl implements PaymentService{

	private PaymentRepository repository;
	
	public PaymentServiceImpl(PaymentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Payment createNewPayment(UUID customerId, UUID orderId,String description, BigDecimal price, PaymentStatus paymentStatus) {
		return repository.save(Payment.builder()
				.orderId(orderId)
				.customerId(customerId)
				.description(description)
				.totalPayment(price)
				.createdAt(new Date())
				.paymentId(UUID.randomUUID())
				.paymentStatus(paymentStatus)
				.build());
	}

	@Override
	public Payment updatePayment(Payment payment) {
		payment.setPaymentId(UUID.randomUUID());
		return repository.save(payment);
	}
	
	@Override
	public Payment findByOrderId(UUID orderId) {
		return repository.findByOrderId(orderId);
	}

	@Override
	public Payment findByPaymentId(UUID paymentId) {
		return repository.getOne(paymentId);
	}
}
