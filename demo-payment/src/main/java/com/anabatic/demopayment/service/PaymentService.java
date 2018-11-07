package com.anabatic.demopayment.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.anabatic.demopayment.entity.Payment;
import com.anabatic.demopayment.record.PaymentStatus;

public interface PaymentService {
	
	public Payment createNewPayment(UUID customerId, UUID orderId, String description,BigDecimal price, PaymentStatus paymentStatus);
	public Payment updatePayment(Payment payment);
	public Payment findByOrderId(UUID orderId);
}
