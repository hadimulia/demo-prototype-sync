package com.anabatic.demopayment.orderaggregator;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrderAggregatorSink {
	String OUTPUT = "order-aggregator-output";

	@Output(OrderAggregatorSink.OUTPUT)
	MessageChannel output();
}
