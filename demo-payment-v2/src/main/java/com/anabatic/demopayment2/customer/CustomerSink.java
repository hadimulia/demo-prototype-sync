package com.anabatic.demopayment2.customer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CustomerSink{
	public String INPUT = "payment-customer-input";
	public String OUTPUT = "payment-customer-output";
	
	@Input(INPUT)
	SubscribableChannel input();
	
	@Output(OUTPUT)
	MessageChannel output();
}
