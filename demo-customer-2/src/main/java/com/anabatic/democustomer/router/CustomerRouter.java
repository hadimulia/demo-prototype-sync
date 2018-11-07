/*package com.anabatic.democustomer.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.anabatic.democustomer.handler.CustomerHandler;

@Configuration
public class CustomerRouter {
	@Bean
	public RouterFunction<ServerResponse> routes(CustomerHandler handler){
		return RouterFunctions.route(RequestPredicates.POST("/customer")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::createCustomer)
				
				;
	}
	
}
*/