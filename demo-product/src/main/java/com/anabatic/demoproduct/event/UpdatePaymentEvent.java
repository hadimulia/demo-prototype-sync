package com.anabatic.demoproduct.event;

import java.util.UUID;

import com.anabatic.demoproduct.enums.PaymentStatus;

import lombok.Data;

@Data
public class UpdatePaymentEvent {

	private UUID orderId;
	private PaymentStatus status;
	private String desc;
}
