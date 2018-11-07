package com.anabatic.demopayment2.customer;

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
public class UpdateBalanceCustomerEvent {
	private UUID customerId;
	private UUID paymentId;
	private BigDecimal balance;
	private String type;
}
