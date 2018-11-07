package com.anabatic.demoorder.service;

import com.anabatic.demoorder.command.ConfirmOrder;
import com.anabatic.demoorder.entity.Order;

public interface OrderConfirmationService {
	public Order confirmOrder(ConfirmOrder confirmOrder);
}
