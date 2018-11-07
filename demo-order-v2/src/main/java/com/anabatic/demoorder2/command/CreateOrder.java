package com.anabatic.demoorder2.command;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class CreateOrder {
	
	private UUID product;
	private BigDecimal price;
	private UUID customerId;
	private Integer qty;
	private String paymentType;
}
