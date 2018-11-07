package com.anabatic.demoorder2.service;

import com.anabatic.demoorder2.command.ConfirmOrder;
import com.anabatic.demoorder2.entity.Order;

public interface OrderConfirmationService {
	public Order confirmOrder(ConfirmOrder confirmOrder);
}
