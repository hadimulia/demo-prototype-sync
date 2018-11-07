package com.anabatic.demoproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import com.anabatic.demoproduct.listener.CloudStreamChannels;
import com.anabatic.demoproduct.listener.ProductSink;

@SpringBootApplication
public class DemoCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCustomerApplication.class, args);
	}
	
    @Configuration
    @EnableBinding({ProductSink.class, CloudStreamChannels.class})
    class StreamConfig {
    }

}
