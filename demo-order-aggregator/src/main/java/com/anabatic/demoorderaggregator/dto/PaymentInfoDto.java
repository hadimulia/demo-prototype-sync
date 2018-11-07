package com.anabatic.demoorderaggregator.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.anabatic.demoorderaggregator.dto.OrderInfoDto.OrderInfoDtoBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class PaymentInfoDto {
	private String statusPayment;
	private String paymentType;
	private String bank;
	private String noRekening;
	private String namaRekening;
}
