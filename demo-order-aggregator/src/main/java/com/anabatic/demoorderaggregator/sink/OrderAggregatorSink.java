package com.anabatic.demoorderaggregator.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderAggregatorSink {
	String INPUT = "orderaggregator-input";

	@Input(OrderAggregatorSink.INPUT)
	SubscribableChannel input();
}
