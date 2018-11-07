package com.anabatic.demoproduct.listener;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.event.TransactionalEventListener;

import com.anabatic.demoproduct.command.ConfirmOrder;
import com.anabatic.demoproduct.event.FindProductEvent;
import com.anabatic.demoproduct.event.UpdateQtyProductEvent;
import com.anabatic.demoproduct.repo.ProductRepository;
import com.anabatic.demoproduct.services.ProductServices;
import com.ati.syd.spring.jms.annotations.ComponentJms;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ComponentJms
public class ProductEventListener {

	private final Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private ProductRepository customerRepository;

	@Autowired
	private ProductServices productServices;

	@Setter
	@Getter
	private UpdateQtyProductEvent updateBalanceCustomerEvent;

	@KafkaListener(topics = "get.info.from.order")
	@TransactionalEventListener
	public void processRequest(Message<FindProductEvent> message) {
		productServices.sendToAgregator(message.getPayload());
	}

	@KafkaListener(topics = "product.update")
	@TransactionalEventListener
	public void processRequestUpdate(Message<UpdateQtyProductEvent> message) {
		productServices.updateProduct(message.getPayload());
	}
}
