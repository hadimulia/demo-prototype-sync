package com.anabatic.demoorder;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import com.anabatic.demoorder.sink.GatewayChannel;
import com.anabatic.demoorder.sink.StreamGateway;
import com.ati.syd.messaging.configuration.ConfigurationMessaging;
import com.ati.syd.spring.jms.ConfigJmsVersion;
import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.anabatic.demoorde")
@AnabaticBEVerison("v1")
@Import(ConfigurationMessaging.class)
public class DemoOrderApplication {
	
	private ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(DemoOrderApplication.class, args);
	}
	
	@Bean
	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
		return configJmsVersion.doOverideJmsListener();
	}
	
	@Bean
	public IntegrationFlow headerEnricherFlow() {
		return IntegrationFlows.from(StreamGateway.ENRICH)
				.enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
				.channel(GatewayChannel.CREATED_OUTPUT).get();
	}
	
	@Bean
	public IntegrationFlow headerEnricherConfirm() {
		return IntegrationFlows.from(StreamGateway.ENRICH_CONFIRM)
				.enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
				.channel(GatewayChannel.CONFIRM_OUTPUT).get();
	}
	
	
}
