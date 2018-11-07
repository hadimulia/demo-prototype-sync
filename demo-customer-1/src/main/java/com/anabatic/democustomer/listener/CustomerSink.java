package com.anabatic.democustomer.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CustomerSink{
	String GET_INFO = "customer-get-info";
	String SEND_INFO = "customer-send-info";
	String CUSTOMER = "customer-v1";
	
	@Input(CustomerSink.GET_INFO)
	SubscribableChannel customer();
	
	@Output(CustomerSink.SEND_INFO)
	MessageChannel customerInfo();
	
	

	@Output(CustomerSink.CUSTOMER)
	MessageChannel customerV1();
}
