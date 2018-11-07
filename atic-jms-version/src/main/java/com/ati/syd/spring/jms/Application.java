package com.ati.syd.spring.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableJms
@ComponentScan("com.ati.syd.spring.jms.rabbitmq")
@AnabaticBEVerison("v2")
public class Application {

	private List<String> stocks = new ArrayList<String>();
	private Map<String, Double> lastPrice = new HashMap<String, Double>();
	private ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(this.getClass());

	{
		stocks.add("AAPL");
		stocks.add("GD");
		stocks.add("BRK.B");

		lastPrice.put("AAPL", 494.64);
		lastPrice.put("GD", 86.74);
		lastPrice.put("BRK.B", 113.59);
	}

	/*
	 * @Autowired JmsTemplate jmsTemplate;
	 */

	@Bean
	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
		return configJmsVersion.doOverideJmsListener();
	}
	
	
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

	/* @Scheduled(fixedRate = 5000L) */// every 5 seconds
	/*
	 * public void publishQuote() {
	 * 
	 * // Pick a random stock symbol Collections.shuffle(stocks); String symbol
	 * = stocks.get(0);
	 * 
	 * // Toss a coin and decide if the price goes... if
	 * (RandomUtils.nextBoolean()) { // ...up by a random 0-10%
	 * lastPrice.put(symbol, new Double(Math.round(lastPrice.get(symbol) * (1 +
	 * RandomUtils.nextInt(10) / 100.0) * 100) / 100)); } else { // ...or down
	 * by a similar random amount lastPrice.put(symbol, new
	 * Double(Math.round(lastPrice.get(symbol) * (1 - RandomUtils.nextInt(10) /
	 * 100.0) * 100) / 100)); }
	 * 
	 * // Log new price locally log.info("Quote..." + symbol + " is now " +
	 * lastPrice.get(symbol));
	 * 
	 * // Coerce a javax.jms.MessageCreator MessageCreator messageCreator =
	 * (Session session) -> { return session.createObjectMessage( "Quote..." +
	 * symbol + " is now " + lastPrice.get(symbol)); };
	 * 
	 * // And publish to RabbitMQ using Spring's JmsTemplate
	 * jmsTemplate.send("rabbit-trader-channel", messageCreator); }
	 */

	public static void main(String[] args) {
		ConfigurableListableBeanFactory  ctx = SpringApplication.run(Application.class, args).getBeanFactory();
		for(String string : ctx.getBeanDefinitionNames()){
			System.out.println(string);
		}
	}
}
