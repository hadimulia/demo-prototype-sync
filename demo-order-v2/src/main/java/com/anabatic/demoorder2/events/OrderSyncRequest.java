package com.anabatic.demoorder2.events;

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
