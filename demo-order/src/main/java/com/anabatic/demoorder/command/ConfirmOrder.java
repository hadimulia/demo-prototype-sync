package com.anabatic.demoorder.command;

import java.util.UUID;

import lombok.Data;

@Data
public class ConfirmOrder {
	private UUID orderId;
	private String pin;
}
