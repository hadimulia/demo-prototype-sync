package com.anabatic.demoproduct.event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

	private UUID productId;
	private UUID orderId;
	
}