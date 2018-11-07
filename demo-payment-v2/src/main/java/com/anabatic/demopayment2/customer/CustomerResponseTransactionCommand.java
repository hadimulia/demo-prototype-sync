package com.anabatic.demopayment2.customer;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerResponseTransactionCommand {
	private UUID customerId;
	private UUID paymentId;
	private String message;
}
