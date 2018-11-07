package com.anabatic.demoproduct.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ProductSink{
	String UPDATE_PRODUCT = "poduct-update";

	String SEND_INFO_TRANSACTION_V1 = "send-info-transaction-v1";
	String SEND_INFO_TRANSACTION_V2 = "send-info-transaction-v2";
	
	String SEND_INFO_ORDER_V1 = "send-info-order-v1";
	String SEND_INFO_ORDER_V2 = "send-info-order-v2";
	
	String GET_INFO_FROM_ORDER = "get-info-from-order";
	String SEND_INFO_ORDER_AGREGATOR = "send-info-order-agregator";
	
	String ROLLBACK_BALANCE = "customer-rollback-balance";
	
	
	@Input(ProductSink.UPDATE_PRODUCT)
	SubscribableChannel updateProductV1();
	
	@Output(ProductSink.SEND_INFO_ORDER_V1)
	MessageChannel senfInfoOrderV1();
	@Output(ProductSink.SEND_INFO_ORDER_V2)
	MessageChannel senfInfoOrderV2();
	
	@Output(ProductSink.ROLLBACK_BALANCE)
	MessageChannel rollbackBalance();
	
	@Output(ProductSink.SEND_INFO_TRANSACTION_V1)
	MessageChannel senfInfoTransactionV1();
	@Output(ProductSink.SEND_INFO_TRANSACTION_V2)
	MessageChannel senfInfoTransactionV2();
	
	@Input(ProductSink.GET_INFO_FROM_ORDER)
	SubscribableChannel getInfoFromOrder();
	@Output(ProductSink.SEND_INFO_ORDER_AGREGATOR)
	MessageChannel senfInfoOrder();
	
	
	
	
}
