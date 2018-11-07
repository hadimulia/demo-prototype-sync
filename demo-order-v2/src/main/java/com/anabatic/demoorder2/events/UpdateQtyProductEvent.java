package com.anabatic.demoorder2.events;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateQtyProductEvent {
	private UUID productId;
	private UUID orderId;
	private UUID customerId;
	private Long qty;
	private BigDecimal totalPayment;
	private String version;
}
