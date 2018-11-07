package com.anabatic.demopayment2.orderaggregator;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderAggregatorEvent {
	private UUID orderId;
	private Object subject;
	private SubjectType subjectType;
}
