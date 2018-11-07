package com.anabatic.demoproduct.command;

import java.util.UUID;

import com.anabatic.demoproduct.enums.ProductStatus;

import lombok.Data;

@Data
public class TransactionInfoCommand {

	private UUID productId;
	private UUID paymentId;
	private ProductStatus status;
	
}
