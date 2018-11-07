package com.anabatic.demoorderaggregator.command;

import java.util.UUID;

import com.anabatic.demoorderaggregator.event.OrderAggregatorEventType;
import com.anabatic.demoorderaggregator.event.SubjectType;

import lombok.Data;

@Data
public class OrderAggregatorCommand {
	private UUID orderId;
	private Object subject;
	private SubjectType subjectType;
	private OrderAggregatorEventType eventType;
}