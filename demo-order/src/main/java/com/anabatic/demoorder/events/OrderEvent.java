package com.anabatic.demoorder.events;

import com.anabatic.demoorder.entity.Order;

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
