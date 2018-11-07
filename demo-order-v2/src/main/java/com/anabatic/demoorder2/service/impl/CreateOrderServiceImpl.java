package com.anabatic.demoorder2.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anabatic.demoorder2.command.CreateOrder;
import com.anabatic.demoorder2.entity.Order;
import com.anabatic.demoorder2.record.OrderStatus;
import com.anabatic.demoorder2.record.RecordStatus;
import com.anabatic.demoorder2.repository.OrderRepository;
import com.anabatic.demoorder2.service.CreateOrderService;
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
					.paymentType(createOrder.getPaymentType())
					.qty(createOrder.getQty())
					.orderId(orderId)
					.customerId(createOrder.getCustomerId())
					.createdAt(new Date())
					.build();
		return repository.save(order);
	}

}
