package com.anabatic.demoorder.command;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
@Data
public class CreateOrder {
	
	private UUID product;
	private BigDecimal price;
	private UUID customerId;
	private Integer qty;
	
}
