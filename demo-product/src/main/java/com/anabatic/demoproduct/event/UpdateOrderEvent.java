package com.anabatic.demoproduct.event;

import java.util.UUID;

import com.anabatic.demoproduct.enums.OrderStatus;

import lombok.Data;

@Data
public class UpdateOrderEvent {

	private UUID orderId;
	private OrderStatus orderStatus;
	private String statusDesc;
}
