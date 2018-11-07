package com.anabatic.demoorderaggregator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anabatic.demoorderaggregator.command.OrderAggregatorCommand;
import com.anabatic.demoorderaggregator.converter.Converter;
import com.anabatic.demoorderaggregator.dto.OrderInfoDto;
import com.anabatic.demoorderaggregator.entity.OrderAggregator;
import com.anabatic.demoorderaggregator.entity.OrderAggregatorDetail;
import com.anabatic.demoorderaggregator.repository.OrderAggregatorDetailRepo;
import com.anabatic.demoorderaggregator.repository.OrderAggregatorRepository;
import com.anabatic.demoorderaggregator.service.OrderAggregatorService;

@Service
public class OrderAggregatorServiceImpl implements OrderAggregatorService {
	private OrderAggregatorRepository orderAggregatorRepository;
	private OrderAggregatorDetailRepo orderAggregatorDetailRepo;
	private Converter converter;

	public OrderAggregatorServiceImpl(OrderAggregatorRepository orderAggregatorRepository, Converter converter,
			OrderAggregatorDetailRepo orderAggregatorDetailRepo) {
		super();
		this.orderAggregatorRepository = orderAggregatorRepository;
		this.converter = converter;
		this.orderAggregatorDetailRepo = orderAggregatorDetailRepo;
	}

	@Override
	public OrderAggregator save(OrderAggregatorCommand orderAggregatorCommand) throws Exception {
		OrderAggregator orderAggregator = this.orderAggregatorRepository
				.findByOrderId(orderAggregatorCommand.getOrderId());
		if (orderAggregator == null) {
			List<OrderAggregatorDetail> orderAggregatorDetails = new ArrayList<>();
			OrderAggregator od = new OrderAggregator(UUID.randomUUID(), orderAggregatorCommand.getOrderId(),
					orderAggregatorDetails, new Date());
			orderAggregatorDetails.add(OrderAggregatorDetail.builder().orderId(orderAggregatorCommand.getOrderId())
					.subject(toByte(orderAggregatorCommand.getSubject())).orderAggregator(od).createdAt(new Date())
					.subjectType(orderAggregatorCommand.getSubjectType()).build());
			od.setOrderAggregatorDetails(orderAggregatorDetails);
			return this.orderAggregatorRepository.save(od);
		} else {
			orderAggregator.getOrderAggregatorDetails()
					.add(OrderAggregatorDetail.builder().orderId(orderAggregatorCommand.getOrderId())
							.subject(toByte(orderAggregatorCommand.getSubject()))
							.subjectType(orderAggregatorCommand.getSubjectType()).createdAt(new Date())
							.orderAggregator(orderAggregator).build());
			return this.orderAggregatorRepository.save(orderAggregator);
		}
	}

	@Override
	public OrderInfoDto orderInfo(UUID orderId) {
		return converter.toDto(this.orderAggregatorDetailRepo.findByOrderId(orderId));
	}

	@Override
	public OrderInfoDto orderInfoSync(UUID orderId) {
		OrderInfoDto orderInfoDto = null;
		int count = 0;
		do {
			orderInfoDto = converter.toDto(this.orderAggregatorDetailRepo.findByOrderId(orderId));
			try {
				Thread.sleep(15L);
			} catch (InterruptedException e) {

			}
			count++;
		} while (orderInfoDto == null && count <= 5);

		return orderInfoDto;
	}

	@Override
	public OrderInfoDto orderInfoConfirm(UUID orderId) {
		OrderInfoDto orderInfoDto = null;
		String status = null;
		int count = 0;
		while ((status == null || status.equals("CREATED") || status.equals("WAITING_PAYMENT")) && count <= 5) {
			status = converter.checkStatus(this.orderAggregatorDetailRepo.findByOrderId(orderId));
			if (!status.equals("CREATED") && !status.equals("WAITING_PAYMENT")) {
				orderInfoDto = converter.toDto(this.orderAggregatorDetailRepo.findByOrderId(orderId));
			}
			try {
				Thread.sleep(15L);
			} catch (InterruptedException e) {

			}
			count++;
		}
		return orderInfoDto;
	}

	public byte[] toByte(Object obj) throws Exception {
		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
		oos2.writeObject(obj);
		return baos2.toByteArray();
	}
}
