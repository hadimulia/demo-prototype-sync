package com.anabatic.demoorderaggregator.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderSyncRequest {
	private UUID orderId;
}
