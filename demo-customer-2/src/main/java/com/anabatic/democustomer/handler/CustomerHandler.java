/*package com.anabatic.democustomer.handler;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.anabatic.democustomer.command.CreateCustomerCommand;
import com.anabatic.democustomer.entity.Customer;
import com.anabatic.democustomer.event.CustomerEvent;
import com.anabatic.democustomer.event.EventType;
import com.anabatic.democustomer.repo.CustomerRepository;

import reactor.core.publisher.Mono;
@Component
public class CustomerHandler {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private Source source;
	
	
	public Mono<ServerResponse> createCustomer(ServerRequest request) {
		Mono<CreateCustomerCommand> createCustomerCommand = request.bodyToMono(CreateCustomerCommand.class);
		return createCustomerCommand.map((command)->{
			return new Customer(UUID.randomUUID(),command.getName(),BigDecimal.ZERO,command.getAddress(),command.getMember());
		})
		.flatMap(customerRepository::save)
		.map(c->{
			System.out.println(c.getId());
			return c;
		})
		.map(newCustomer->source.output().send(MessageBuilder.withPayload(new CustomerEvent(newCustomer,EventType.CUSTOMER_CREATED)).build()))
		.flatMap(m->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build());
	}
	
	
	
}
*/