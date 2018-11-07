package com.anabatic.demoorder2.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.anabatic.demoorder2.record.OrderStatus;
import com.anabatic.demoorder2.record.RecordStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="\"order\"")
public class Order {
	
	@Id
	private UUID id;
	private UUID orderId;
	private UUID customerId;
	private UUID product;
	private String paymentType;
	private Integer qty;
	private BigDecimal productPrice;
	@Enumerated(EnumType.STRING)
	private RecordStatus recordStatus;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	private String statusDesc;
	private Date createdAt;
}
