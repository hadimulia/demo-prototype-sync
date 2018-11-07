package com.anabatic.demopayment2.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anabatic.demopayment2.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,UUID>{
	@Query(value = "FROM Payment WHERE orderId =:orderId AND createdAt = (SELECT MAX(createdAt) FROM Payment WHERE orderId =:orderId)")
	Payment findByOrderId(UUID orderId);
}
