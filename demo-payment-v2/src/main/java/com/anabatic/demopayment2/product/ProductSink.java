package com.anabatic.demopayment2.product;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductSink {
	String INPUT = "update-product";
	
	@Input(ProductSink.INPUT)
	SubscribableChannel input();
}
