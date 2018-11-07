package com.anabatic.democustomer;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.anabatic.democustomer.listener.CustomerSink;
import com.ati.syd.messaging.configuration.ConfigurationMessaging;
import com.ati.syd.spring.jms.ConfigJmsVersion;
import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

@SpringBootApplication
@ComponentScan({"com.anabatic.democustomer"})
@AnabaticBEVerison("v1")
@Import(ConfigurationMessaging.class)
public class DemoCustomerApplication {

	private ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(DemoCustomerApplication.class, args);
	}
	
	@Bean
	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
		return configJmsVersion.doOverideJmsListener();
	}


}
