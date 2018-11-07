package com.anabatic.democustomer.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerRespoinseTransactionCommand {

	private UUID customerId;
	private String message;
	private UUID paymentId;
}
