package com.anabatic.demoorder.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anabatic.demoorder.entity.Order;
import com.anabatic.demoorder.events.UpdateOrderEvent;
import com.anabatic.demoorder.record.OrderStatus;
import com.anabatic.demoorder.record.RecordStatus;
import com.anabatic.demoorder.repository.OrderRepository;
import com.anabatic.demoorder.service.UpdateOrderService;

@Service
public class UpdateOrderServiceImpl implements UpdateOrderService {

	private OrderRepository orderRepository;

	public UpdateOrderServiceImpl(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	@Override
	public Order updateOrder(UpdateOrderEvent updateOrderEvent) {
		Order current = orderRepository.findByOrderId(updateOrderEvent.getOrderId());
		Order updated = Order.builder().id(UUID.randomUUID())
				.orderStatus(OrderStatus.valueOf(updateOrderEvent.getOrderStatus()))
				.recordStatus(RecordStatus.UPDATED)
				.productPrice(current.getProductPrice())
				.product(current.getProduct())
				.qty(current.getQty())
				.statusDesc(updateOrderEvent.getStatusDesc())
				.orderId(updateOrderEvent.getOrderId())
				.customerId(current.getCustomerId())
				.createdAt(new Date())
				.build();
		return orderRepository.save(updated);
	}

}
