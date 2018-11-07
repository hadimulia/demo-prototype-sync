package com.ati.syd.messaging.configuration;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.ati.syd.messaging.enums.MessagingProviderEnum;

/**
 * Configuration for messaging using provider ActiveMQ. <br>
 * Example default configuration in springboot application.propertiees
 * <br><br>
 * spring.activemq.broker-url=tcp://localhost:61616 <br>
   spring.activemq.user=admin <br>
   spring.activemq.password=admin <br>
 *<br>
 * also you can see the <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html">common application properties</a> 
 * @author taufik.muliahadi (&copy;Nov 2, 2018) 
 */
@Profile(MessagingProviderEnum.ACTIVEMQ)
@EnableJms
@Configuration
public class ConfigurationMessaging {

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}
