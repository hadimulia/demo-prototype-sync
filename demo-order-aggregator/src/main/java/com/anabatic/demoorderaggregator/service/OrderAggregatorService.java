package com.anabatic.demoorderaggregator.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.anabatic.demoorderaggregator.command.OrderAggregatorCommand;
import com.anabatic.demoorderaggregator.dto.OrderInfoDto;
import com.anabatic.demoorderaggregator.entity.OrderAggregator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderAggregatorService {
	OrderAggregator save(OrderAggregatorCommand orderAggregatorCommand) throws Exception;
	OrderInfoDto orderInfo(UUID orderId);
	OrderInfoDto orderInfoSync(UUID orderId);
	OrderInfoDto orderInfoConfirm(UUID orderId);
}
