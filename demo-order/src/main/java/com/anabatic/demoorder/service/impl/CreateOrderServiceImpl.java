package com.anabatic.demoorder.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anabatic.demoorder.command.CreateOrder;
import com.anabatic.demoorder.entity.Order;
import com.anabatic.demoorder.record.OrderStatus;
import com.anabatic.demoorder.record.RecordStatus;
import com.anabatic.demoorder.repository.OrderRepository;
import com.anabatic.demoorder.service.CreateOrderService;
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

	private OrderRepository repository;
	
	public CreateOrderServiceImpl(OrderRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public Order createOrder(CreateOrder createOrder) {
		UUID recordId = UUID.randomUUID();
		UUID orderId = UUID.randomUUID();
		Order order = Order.builder().id(recordId)
					.orderStatus(OrderStatus.CREATED)
					.recordStatus(RecordStatus.CREATED)
					.productPrice(createOrder.getPrice())
					.product(createOrder.getProduct())
					.qty(createOrder.getQty())
					.orderId(orderId)
					.customerId(createOrder.getCustomerId())
					.createdAt(new Date())
					.build();
		return repository.save(order);
	}

}
