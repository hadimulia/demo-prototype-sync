package com.anabatic.demoorder2.service;

import com.anabatic.demoorder2.entity.Order;
import com.anabatic.demoorder2.events.UpdateOrderEvent;

public interface UpdateOrderService {
	public Order updateOrder(UpdateOrderEvent updateOrderEvent);
}
