package com.anabatic.demopayment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import com.anabatic.demopayment2.orderaggregator.OrderAggregatorSink;
import com.anabatic.demopayment2.product.ProductSink;
import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

@SpringBootApplication
@AnabaticBEVerison("v2")
public class DemoPaymentV2Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoPaymentV2Application.class, args);
	}
	@Configuration
    @EnableBinding({OrderAggregatorSink.class, ProductSink.class})
    class OrderAggregatorConfig {
    }
}
