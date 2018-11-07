package com.anabatic.demoorderaggregator.command;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderInfoCommand {
	private UUID orderId;
	private String pin;
}
