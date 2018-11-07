package com.anabatic.democustomer.command;

import java.util.UUID;

import com.anabatic.democustomer.enums.OrderAggregatorEventType;
import com.anabatic.democustomer.enums.SubjectType;

import lombok.Data;

@Data
public class OrderAggregatorCommand {

	private UUID orderId;
	private Object subject;
	private SubjectType subjectType;
}
