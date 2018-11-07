package com.anabatic.demoorder2;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import com.anabatic.demoorder2.sink.CustomerSink;
import com.anabatic.demoorder2.sink.GatewayChannel;
import com.anabatic.demoorder2.sink.OrderAggregatorSink;
import com.anabatic.demoorder2.sink.ProductSink;
import com.anabatic.demoorder2.sink.StreamGateway;
import com.ati.syd.messaging.configuration.ConfigurationMessaging;
import com.ati.syd.spring.jms.ConfigJmsVersion;
import com.ati.syd.spring.jms.annotations.AnabaticBEVerison;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.anabatic.demoorder2")
@AnabaticBEVerison("v2")
@Import(ConfigurationMessaging.class)
public class DemoOrderV2Application {

	private ConfigJmsVersion configJmsVersion = new ConfigJmsVersion(this.getClass());
	
	@Bean
	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
		return configJmsVersion.doOverideJmsListener();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoOrderV2Application.class, args);
	}

	@Configuration
	@EnableBinding({ Source.class, OrderAggregatorSink.class, CustomerSink.class, ProductSink.class, GatewayChannel.class})
	class StreamConfig {
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
}
