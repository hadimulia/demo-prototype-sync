package com.anabatic.demoproduct.event;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateQtyProductEvent {

	private UUID productId;
	private UUID orderId;
	private UUID customerId;
	private Long qty;
	private BigDecimal totalPayment;
	private String version;
	
}
