package com.anabatic.demoorder2.service;

import com.anabatic.demoorder2.command.CreateOrder;
import com.anabatic.demoorder2.entity.Order;

public interface CreateOrderService {
	public Order createOrder(CreateOrder createOrder);
}
