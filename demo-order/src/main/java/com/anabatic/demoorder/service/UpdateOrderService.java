package com.anabatic.demoorder.service;

import com.anabatic.demoorder.entity.Order;
import com.anabatic.demoorder.events.UpdateOrderEvent;

public interface UpdateOrderService {
	public Order updateOrder(UpdateOrderEvent updateOrderEvent);
}
