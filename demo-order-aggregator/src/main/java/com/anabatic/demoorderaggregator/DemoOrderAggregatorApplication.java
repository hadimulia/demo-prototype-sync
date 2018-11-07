package com.anabatic.demoorderaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.anabatic.demoorderaggregator.event.CloudStreamChannels;
import com.ati.syd.messaging.configuration.ConfigurationMessaging;

@SpringBootApplication
@EnableDiscoveryClient
@Import(ConfigurationMessaging.class)
public class DemoOrderAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoOrderAggregatorApplication.class, args);
	}
	
	 @Configuration
	    @EnableBinding({CloudStreamChannels.class})
	    class StreamConfig {
	    }
}
