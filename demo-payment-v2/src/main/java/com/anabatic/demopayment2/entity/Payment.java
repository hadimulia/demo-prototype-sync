package com.anabatic.demopayment2.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.anabatic.demopayment2.record.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="payment")
public class Payment {
	@Id
	private UUID paymentId;
	private UUID orderId;
	private UUID customerId;
	private String description;
	private BigDecimal totalPayment;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private Date createdAt;
}
