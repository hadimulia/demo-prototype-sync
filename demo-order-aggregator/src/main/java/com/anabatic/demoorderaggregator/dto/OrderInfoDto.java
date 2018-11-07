package com.anabatic.demoorderaggregator.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class OrderInfoDto {
	private String orderId;
	private String customerName;
	private String productName;
	private String qty;
	private BigDecimal totalPayment;
	private String statusOrder;
	private String statusDesc;
	private PaymentInfoDto paymentInfo;
}
