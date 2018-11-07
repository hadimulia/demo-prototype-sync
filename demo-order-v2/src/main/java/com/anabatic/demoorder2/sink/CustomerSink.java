package com.anabatic.demoorder2.sink;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomerSink {
	String OUTPUT = "customer-output";
	
	@Output(CustomerSink.OUTPUT)
	MessageChannel output();
}
