package com.anabatic.demoproduct.command;

import java.util.UUID;

import com.anabatic.demoproduct.enums.OrderAggregatorEventType;
import com.anabatic.demoproduct.enums.SubjectType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderAggregatorCommand {

	private UUID orderId;
	private Object subject;
	private SubjectType subjectType;
	private OrderAggregatorEventType eventType;
}
