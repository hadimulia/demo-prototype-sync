/*package com.anabatic.demoproduct.handler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.anabatic.demoproduct.command.CreateProductCommand;
import com.anabatic.demoproduct.entity.Product;
import com.anabatic.demoproduct.repo.ProductRepository;

import reactor.core.publisher.Mono;
@Component
public class ProductHandler {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	public Mono<ServerResponse> productCustomer(ServerRequest request) {
		Mono<CreateProductCommand> createProductCommand = request.bodyToMono(CreateProductCommand.class);
		return createProductCommand.map((command)->{
			return new Product(UUID.randomUUID(), command.getName(), command.getQty(), command.getDesc());
		})
		.flatMap(productRepository::save)
		.map(c->{
			System.out.println(c.getId());
			return c;
		})
		.flatMap(m->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build());
	}
	
	
	
}
*/