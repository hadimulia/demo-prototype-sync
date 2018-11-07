package com.anabatic.demoorder2.product;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductSink {
	String OUTPUT = "product-output";
	
	@Output(ProductSink.OUTPUT)
	MessageChannel output();
}
