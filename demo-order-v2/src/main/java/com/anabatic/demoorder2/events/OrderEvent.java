package com.anabatic.demoorder2.events;

import com.anabatic.demoorder2.entity.Order;

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
