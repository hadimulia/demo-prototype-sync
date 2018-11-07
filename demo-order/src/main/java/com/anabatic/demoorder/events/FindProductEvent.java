package com.anabatic.demoorder.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FindProductEvent {
	private UUID productId;
	private UUID orderId;
}
