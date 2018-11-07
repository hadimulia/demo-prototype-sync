package com.ati.syd.messaging.listener;


import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.ati.syd.spring.jms.annotations.ComponentJms;

@Component
public class ListenerMessage {

	@KafkaListener(topics="test.topcis",groupId="test.group")
	public void sampleListener(Message<Object> message)  {
		System.out.println(message.getPayload());
	}
	
	@KafkaListener(topics="test.topcis",groupId="test.group")
	public void sampleListener2(Message<Object> message)  {
		System.out.println(message.getPayload());
	}
	
}
