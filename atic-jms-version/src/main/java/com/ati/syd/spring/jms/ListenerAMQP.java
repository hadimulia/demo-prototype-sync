package com.ati.syd.spring.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.annotation.JmsListener;

import com.ati.syd.spring.jms.annotations.ComponentJms;

@ComponentJms
public class ListenerAMQP {

	private static final Log log = LogFactory.getLog(ListenerAMQP.class);

	@JmsListener(destination="test.queue.backend")
	public void listenerRabbit(String message){
		log.info("masuk listening");
	}
	
	@JmsListener(destination="test.queue.frontend")
	public void listenerRabbit2(String message){
		log.info("masuk listening");
	}

	static class Receiver {
		public void receive(String message) {
			log.info("Received " + message);
		}
	}
}
