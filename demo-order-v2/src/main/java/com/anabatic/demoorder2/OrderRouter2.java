/*package com.anabatic.demoorder2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.anabatic.demoorder2.handler.OrderController;

@Configuration
public class OrderRouter2 {
	
	@Bean
	public RouterFunction<ServerResponse> routes(OrderHandler handler){
		
		return RouterFunctions
				.route(RequestPredicates.POST("/create-order")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::createOrder)
				.andRoute(RequestPredicates.POST("/confirm-order")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::confirmOrder)
				.andRoute(RequestPredicates.POST("/check-order")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::checkOrder)
				;
	}
}
*/