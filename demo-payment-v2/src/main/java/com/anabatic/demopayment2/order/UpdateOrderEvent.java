package com.anabatic.demopayment2.order;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderEvent {
	private UUID orderId;
	private String orderStatus;
	private String statusDesc;
}
