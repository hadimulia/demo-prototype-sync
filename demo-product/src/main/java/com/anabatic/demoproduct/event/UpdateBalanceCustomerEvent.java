package com.anabatic.demoproduct.event;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class UpdateBalanceCustomerEvent {

	private UUID customerId;
	private UUID paymentId;
	private BigDecimal balance;
	private EventUpdateBalanceType type;
}
