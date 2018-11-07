package com.anabatic.demoorder2.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anabatic.demoorder2.entity.Order;
import com.anabatic.demoorder2.events.UpdateOrderEvent;
import com.anabatic.demoorder2.record.OrderStatus;
import com.anabatic.demoorder2.record.RecordStatus;
import com.anabatic.demoorder2.repository.OrderRepository;
import com.anabatic.demoorder2.service.UpdateOrderService;

@Service
public class UpdateOrderServiceImpl implements UpdateOrderService {

	private OrderRepository repository;

	public UpdateOrderServiceImpl(OrderRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Order updateOrder(UpdateOrderEvent updateOrderEvent) {
		Order current = repository.findByOrderId(updateOrderEvent.getOrderId());
		Order updated = Order.builder().id(UUID.randomUUID())
				.orderStatus(OrderStatus.valueOf(updateOrderEvent.getOrderStatus()))
				.recordStatus(RecordStatus.UPDATED)
				.productPrice(current.getProductPrice())
				.product(current.getProduct())
				.qty(current.getQty())
				.paymentType(current.getPaymentType())
				.statusDesc(updateOrderEvent.getStatusDesc())
				.orderId(updateOrderEvent.getOrderId())
				.customerId(current.getCustomerId())
				.createdAt(new Date())
				.build();
		return repository.save(updated);
	}
}
