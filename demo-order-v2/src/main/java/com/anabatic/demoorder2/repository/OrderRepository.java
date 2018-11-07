package com.anabatic.demoorder2.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anabatic.demoorder2.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID>{
	@Query(value = "FROM Order WHERE orderId =:orderId AND createdAt = (SELECT MAX(createdAt) FROM Order WHERE orderId =:orderId)")
	Order findByOrderId(@Param("orderId") UUID orderId);
}