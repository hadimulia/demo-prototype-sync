package com.anabatic.democustomer.handler;

import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabatic.democustomer.command.CreateCustomerCommand;
import com.anabatic.democustomer.entity.Customer;
import com.anabatic.democustomer.repo.CustomerServices;
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerServices customerServices;
	
	@PostMapping("")
	public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerCommand body ,HttpServletRequest request,HttpServletResponse response) {
		CreateCustomerCommand command = body;
		Customer customer =  new Customer(UUID.randomUUID(),command.getName(),BigDecimal.ZERO,command.getAddress());
		System.out.println(customer.getId());
		customerServices.saveAndSend(customer);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(customer);
	}
	
	
}
