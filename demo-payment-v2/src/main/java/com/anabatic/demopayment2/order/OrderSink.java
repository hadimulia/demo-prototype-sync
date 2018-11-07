package com.anabatic.demopayment2.order;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderSink{
	public String INPUT = "payment-order-input";
	public String OUTPUT = "payment-order-output";
	
	@Input(INPUT)
	SubscribableChannel order();
	
	@Output(OUTPUT)
	MessageChannel output();
}
