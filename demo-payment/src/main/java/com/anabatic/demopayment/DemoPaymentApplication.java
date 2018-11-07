package com.anabatic.demopayment;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.anabatic.demopayment.orderaggregator.OrderAggregatorSink;
import com.ati.syd.messaging.configuration.ConfigurationMessaging;
import com.ati.syd.spring.jms.ConfigJmsVersion;
import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.anabatic.demopayment")
@AnabaticBEVerison("v1")
@Import(ConfigurationMessaging.class)
public class DemoPaymentApplication {

	private ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(this.getClass());
	
	@Bean
	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
		return configJmsVersion.doOverideJmsListener();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoPaymentApplication.class, args);
	}
	@Configuration
    @EnableBinding(OrderAggregatorSink.class)
    class OrderAggregatorConfig {
    }
}
