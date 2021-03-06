package com.anabatic.demoorder.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderSink {
	String INPUT = "order-input";

	@Input(OrderSink.INPUT)
	SubscribableChannel order();
}
