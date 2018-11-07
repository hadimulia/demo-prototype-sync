package com.anabatic.demoproduct.command;

import java.util.UUID;

import lombok.Data;

@Data
public class ConfirmOrder {
	private UUID orderId;
	private String pin;
}
