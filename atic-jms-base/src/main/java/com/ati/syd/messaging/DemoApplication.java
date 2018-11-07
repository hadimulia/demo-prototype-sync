package com.ati.syd.messaging;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.ati.syd.messaging.configuration.ConfigurationMessaging;
import com.ati.syd.spring.jms.ConfigJmsVersion;
import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

@SpringBootApplication
@ComponentScan("com.ati.syd.messaging")
//@AnabaticBEVerison("v1")
@Import(ConfigurationMessaging.class)
public class DemoApplication {

	
	private ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
//	@Bean
//	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
//		return configJmsVersion.doOverideJmsListener();
//	}

	
}
