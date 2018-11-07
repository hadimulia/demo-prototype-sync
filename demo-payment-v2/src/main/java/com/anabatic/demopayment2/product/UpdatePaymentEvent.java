package com.anabatic.demopayment2.product;

import java.util.UUID;

import com.anabatic.demopayment2.record.PaymentStatus;

import lombok.Data;

@Data
public class UpdatePaymentEvent {
	private UUID orderId;
	private PaymentStatus status;
	private String desc;
}