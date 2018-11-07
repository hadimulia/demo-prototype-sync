package com.anabatic.demopayment.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	
	@Id
	private UUID id;
	private UUID orderId;
	private UUID customerId;
	private String product;
	private Integer qty;
	private BigDecimal productPrice;
	private String recordStatus;
	private String orderStatus;
	private Date createdAt;
}
