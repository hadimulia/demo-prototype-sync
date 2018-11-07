package com.anabatic.demopayment.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anabatic.demopayment.entity.Payment;

import reactor.core.publisher.Flux;

public interface PaymentRepository extends JpaRepository<Payment,UUID>{
	@Query(value = "FROM Payment WHERE orderId =:orderId AND createdAt = (SELECT MAX(createdAt) FROM Payment WHERE orderId =:orderId)")
	Payment findByOrderId(UUID orderId);
}
