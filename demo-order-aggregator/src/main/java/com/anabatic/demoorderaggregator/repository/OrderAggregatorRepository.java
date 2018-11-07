package com.anabatic.demoorderaggregator.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anabatic.demoorderaggregator.entity.OrderAggregator;

import reactor.core.publisher.Flux;

public interface OrderAggregatorRepository extends JpaRepository<OrderAggregator, UUID>{
	OrderAggregator findByOrderId(UUID orderId);
}
