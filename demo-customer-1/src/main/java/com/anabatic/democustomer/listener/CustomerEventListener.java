package com.anabatic.democustomer.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;

import com.anabatic.democustomer.event.FindCustomerEvent;
import com.anabatic.democustomer.repo.CustomerServices;
import com.ati.syd.spring.jms.annotations.ComponentJms;

@ComponentJms
public class CustomerEventListener {

	private final Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private CustomerServices customerServices;

	@KafkaListener(topics="customer.find.id")
	public void processRequest(Message<FindCustomerEvent> message) {
		log.info("receive request : " + message.getPayload());
		customerServices.sendToAgregator(message.getPayload());

	}
}
