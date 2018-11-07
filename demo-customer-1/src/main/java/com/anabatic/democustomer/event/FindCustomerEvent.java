package com.anabatic.democustomer.event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FindCustomerEvent {

	private UUID customerId;
	private UUID orderId;
	private String pin;
	
}
