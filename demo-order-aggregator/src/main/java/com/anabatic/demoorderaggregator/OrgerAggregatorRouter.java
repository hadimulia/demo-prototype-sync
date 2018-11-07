/*package com.anabatic.demoorderaggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.anabatic.demoorderaggregator.handler.OrderAggregatorHandler;

@Configuration
public class OrgerAggregatorRouter {
	
	@Bean
	public RouterFunction<ServerResponse> routes(OrderAggregatorHandler handler){
		
		return RouterFunctions
				.route(RequestPredicates.POST("/create-order")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::createOrder)
				.andRoute(RequestPredicates.POST("/info-order")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::viewOrder)
				;
	}
}
*/