package com.anabatic.cloud.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.anabatic.cloud.router.filter.RouterFilter;

@SpringBootApplication(scanBasePackages="com.anabatic.cloud.router")
@EnableZuulProxy
@EnableEurekaClient
public class CloudRouterApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	public static void main(String[] args) {
		SpringApplication.run(CloudRouterApplication.class, args);
	}
	@Bean
	public RouterFilter preFilter() {
		return new RouterFilter(discoveryClient);
	}
}
