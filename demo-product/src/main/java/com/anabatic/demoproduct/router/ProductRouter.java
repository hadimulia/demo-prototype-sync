/*package com.anabatic.demoproduct.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.anabatic.demoproduct.handler.ProductHandler;

@Configuration
public class ProductRouter {
	@Bean
	public RouterFunction<ServerResponse> routes(ProductHandler handler){
		return RouterFunctions.route(RequestPredicates.POST("/product")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::productCustomer)
				
				;
	}
	
}
*/