package com.anabatic.demoorder2.events;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateOrderEvent {
	private UUID orderId;
	private String orderStatus;
	private String statusDesc;
}
