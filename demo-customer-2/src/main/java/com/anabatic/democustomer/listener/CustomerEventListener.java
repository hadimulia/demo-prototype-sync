package com.anabatic.democustomer.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;

import com.anabatic.democustomer.event.FindCustomerEvent;
import com.anabatic.democustomer.event.UpdateBalanceCustomerEvent;
import com.anabatic.democustomer.services.CustomerServices;
import com.ati.syd.spring.jms.annotations.ComponentJms;

import lombok.Getter;
import lombok.Setter;

@ComponentJms
public class CustomerEventListener {

	private final Logger log = LogManager.getLogger(this.getClass());


	@Autowired
	private CustomerServices customerServices;

	@Setter
	@Getter
	private UpdateBalanceCustomerEvent updateBalanceCustomerEvent;

	@KafkaListener(topics="customer.find.id")
	public void processRequest(Message<FindCustomerEvent> message) {
		FindCustomerEvent mono = message.getPayload();
		log.info("receive request : " + message.getPayload());
		customerServices.sendToAgregator(mono);
	}

	@KafkaListener(topics="customer.update.balance")
	public void processRequestUpdate(Message<UpdateBalanceCustomerEvent> message) {
		customerServices.updateBalance(message.getPayload());
	}

	@KafkaListener(topics="customer.rollback.balance")
	public void processRequestRollback(Message<UpdateBalanceCustomerEvent> message) {
		customerServices.updateBalance(message.getPayload());
	}

}
