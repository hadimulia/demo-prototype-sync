package com.anabatic.democustomer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.anabatic.democustomer.command.CustomerRespoinseTransactionCommand;
import com.anabatic.democustomer.command.OrderAggregatorCommand;
import com.anabatic.democustomer.entity.Customer;
import com.anabatic.democustomer.enums.SubjectType;
import com.anabatic.democustomer.event.CustomerEvent;
import com.anabatic.democustomer.event.EventType;
import com.anabatic.democustomer.event.EventUpdateBalanceType;
import com.anabatic.democustomer.event.FindCustomerEvent;
import com.anabatic.democustomer.event.UpdateBalanceCustomerEvent;
import com.anabatic.democustomer.listener.CustomerSink;
import com.anabatic.democustomer.repo.CustomerRepository;
import com.anabatic.democustomer.util.HibernateProxyUtil;
import com.ati.syd.messaging.produce.MessagingProduce;

import reactor.core.publisher.Mono;

@Component
public class CustomerServices {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerSink customerSink;
	
	@Autowired
	private MessagingProduce<Object> messagingProduce;
	
	public CustomerRespoinseTransactionCommand updateBalance(UpdateBalanceCustomerEvent event) {
		
		Customer cus = HibernateProxyUtil.initializeAndUnproxy(customerRepository.getOne(event.getCustomerId()));
		CustomerRespoinseTransactionCommand response =  new CustomerRespoinseTransactionCommand();
		response.setCustomerId(cus.getId());
		response.setPaymentId(event.getPaymentId());
		if(event.getType().equals(EventUpdateBalanceType.DEBIT)) {
			if(cus.getBalance().compareTo(event.getBalance()) < 0) {
				response.setMessage("Transaction unsuccessful, cause Insuficient Balance");
			}else {
				cus.setBalance(cus.getBalance().add(event.getBalance().negate()));
				response.setMessage("Transaction Successful");
				customerRepository.save(cus);
			}
		}else {
			cus.setBalance(cus.getBalance().add(event.getBalance()));
			response.setMessage("Transaction Successful");
			customerRepository.save(cus);
		}
		
		
		if(null!=event.getPaymentId() )
			messagingProduce.produceAsynhcronous("transaction.response.customer.v2", response);
			
		return response;
	}
	
	public void sendToAgregator(FindCustomerEvent findCustomerEvent) {
		Customer customer =  customerRepository.getOne(findCustomerEvent.getCustomerId());
		
		OrderAggregatorCommand orderAggregatorCommand = new OrderAggregatorCommand();
		orderAggregatorCommand.setOrderId(findCustomerEvent.getOrderId());
		orderAggregatorCommand.setSubject(HibernateProxyUtil.initializeAndUnproxy(customer));
		orderAggregatorCommand.setSubjectType(SubjectType.CUSTOMER);
		
		messagingProduce.produceAsynhcronous("order.aggregator.update", orderAggregatorCommand);
	}
	
	public Customer saveAndSend(Customer customer) {
		messagingProduce.produceAsynhcronous("customer.v2", new CustomerEvent(customer,EventType.CUSTOMER_CREATED));
		return customerRepository.save(customer);
	}
}
