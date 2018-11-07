package com.anabatic.demoorder.sink;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductSink {
	String OUTPUT = "product-output";
	String OUTPUT_UPDATE = "update-product-output";

	@Output(ProductSink.OUTPUT)
	MessageChannel output();
	
	@Output(ProductSink.OUTPUT_UPDATE)
	MessageChannel outputUpdate();
}
