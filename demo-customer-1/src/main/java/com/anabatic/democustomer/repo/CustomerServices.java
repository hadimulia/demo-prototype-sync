package com.anabatic.democustomer.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.anabatic.democustomer.command.OrderAggregatorCommand;
import com.anabatic.democustomer.entity.Customer;
import com.anabatic.democustomer.enums.SubjectType;
import com.anabatic.democustomer.event.CustomerEvent;
import com.anabatic.democustomer.event.EventType;
import com.anabatic.democustomer.event.FindCustomerEvent;
import com.anabatic.democustomer.listener.CustomerSink;
import com.anabatic.democustomer.util.HibernateProxyUtil;
import com.ati.syd.messaging.produce.MessagingProduce;

@Component
public class CustomerServices {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerSink customerSink;
	
	@Autowired
	private MessagingProduce<Object> messagingProduce;
	
	
	public void sendToAgregator(FindCustomerEvent findCustomerEvent) {
		Customer customer =  customerRepository.getOne(findCustomerEvent.getCustomerId());
		
		OrderAggregatorCommand orderAggregatorCommand = new OrderAggregatorCommand();
		orderAggregatorCommand.setOrderId(findCustomerEvent.getOrderId());
		orderAggregatorCommand.setSubject(HibernateProxyUtil.initializeAndUnproxy(customer));
		orderAggregatorCommand.setSubjectType(SubjectType.CUSTOMER);
		
		messagingProduce.produceAsynhcronous("order.aggregator.update", orderAggregatorCommand);
	}
	public Customer saveAndSend(Customer customer) {
		messagingProduce.produceAsynhcronous("customer.v1", new CustomerEvent(customer,EventType.CUSTOMER_CREATED));
		return customerRepository.save(customer);
	}
}
