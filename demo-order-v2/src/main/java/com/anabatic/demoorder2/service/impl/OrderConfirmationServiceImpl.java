package com.anabatic.demoorder2.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.anabatic.demoorder2.command.ConfirmOrder;
import com.anabatic.demoorder2.entity.Order;
import com.anabatic.demoorder2.record.OrderStatus;
import com.anabatic.demoorder2.record.RecordStatus;
import com.anabatic.demoorder2.repository.OrderRepository;
import com.anabatic.demoorder2.service.OrderConfirmationService;

@Service
public class OrderConfirmationServiceImpl implements OrderConfirmationService {

	private OrderRepository repository;

	public OrderConfirmationServiceImpl(OrderRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Order confirmOrder(ConfirmOrder confirmOrder) {
		Order current = repository.findByOrderId(confirmOrder.getOrderId());
		if (current != null) {
			Order order = new Order(UUID.randomUUID(), 
					current.getOrderId(), current.getCustomerId(),
					current.getProduct(), current.getPaymentType(), 
					current.getQty(), current.getProductPrice(),
					RecordStatus.UPDATED, OrderStatus.SUCCESSFULL, 
					current.getStatusDesc(), new Date());
			return repository.save(order);
		} else {
			throw new EntityNotFoundException();
		}
	}
}
