package com.anabatic.demoorder.service;

import com.anabatic.demoorder.command.CreateOrder;
import com.anabatic.demoorder.entity.Order;

public interface CreateOrderService {
	public Order createOrder(CreateOrder createOrder);
}
