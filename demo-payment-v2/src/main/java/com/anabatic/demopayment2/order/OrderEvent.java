package com.anabatic.demopayment2.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
	private Order subject;
	private OrderEventType eventType;
}
