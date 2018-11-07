package com.anabatic.democustomer.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CustomerSink{
	String OUTPUT = "customer-transaction";
	String UPDATE_BALANCE = "customer-update-balance";
	String GET_INFO = "customer-get-info";
	String SEND_INFO = "customer-send-info";
	String ROLLBACK_BALANCE = "customer-rollback-balance";
	
	String CUSTOMER = "customer-v2";
	
	@Output(CustomerSink.CUSTOMER)
	MessageChannel customer();
	
	@Input(CustomerSink.GET_INFO)
	SubscribableChannel customerGetInfo();
	
	@Output(CustomerSink.SEND_INFO)
	MessageChannel customerSendInfo();
	
	@Input(CustomerSink.UPDATE_BALANCE)
	SubscribableChannel updateBalance();
	
	@Output(CustomerSink.OUTPUT)
	MessageChannel transaction();
	
	@Output(CustomerSink.ROLLBACK_BALANCE)
	MessageChannel rollback();
}
