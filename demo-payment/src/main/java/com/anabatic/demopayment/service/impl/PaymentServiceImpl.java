package com.anabatic.demopayment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.anabatic.demopayment.entity.Payment;
import com.anabatic.demopayment.record.PaymentStatus;
import com.anabatic.demopayment.repository.PaymentRepository;
import com.anabatic.demopayment.service.PaymentService;

import reactor.core.publisher.Mono;
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
	
}
