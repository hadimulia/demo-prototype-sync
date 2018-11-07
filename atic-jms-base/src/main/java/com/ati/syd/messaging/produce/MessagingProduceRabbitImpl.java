package com.ati.syd.messaging.produce;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ati.syd.messaging.enums.MessagingProviderEnum;
import com.ati.syd.messaging.template.RequestMessage;
import com.ati.syd.messaging.template.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
@Profile(MessagingProviderEnum.RABBITMQ)
public class MessagingProduceRabbitImpl<T> extends MessagingProduceImpl<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected RabbitTemplate jmsTemplate;

	protected Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public T produce(String topic, T obj) {
		String processId = UUID.randomUUID().toString();
		MessagePostProcessor mpp = new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setContentType("application/json");
				message.getMessageProperties().setHeader("processId", processId);
				return message;
			}
		};
		jmsTemplate.convertAndSend(topic, obj,mpp);
		return obj;
	}

	@Override
	public T produce(String topic, String group, T obj) {
		String processId = UUID.randomUUID().toString();
		MessagePostProcessor mpp = new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setContentType("application/json");
				message.getMessageProperties().setHeader("processId", processId);
				return message;
			}
		};
		jmsTemplate.convertAndSend(topic,group, obj,mpp);
		return obj;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public ResponseMessage<T> produce(RequestMessage<T> request) {
		String processId = UUID.randomUUID().toString();
		ResponseMessage<T> responseMessage = new ResponseMessage<T>();
		MessagePostProcessor mpp = new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setContentType("application/json");
				message.getMessageProperties().setHeader("processId", processId);
				return message;
			}
		};
		try {
			if(request.getGroup() == null && request.equals(""))
				jmsTemplate.convertAndSend(request.getTopic(), request.getRequest(), mpp);
			else
				jmsTemplate.convertAndSend(request.getTopic(),request.getGroup(), request.getRequest(), mpp);
			responseMessage.setCode(String.valueOf(HttpStatus.OK.value()));
			responseMessage.setRequestId(processId);
			responseMessage.setMessage(HttpStatus.OK.getReasonPhrase());
			responseMessage.setRequest(request.getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseMessage.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
			responseMessage.setRequestId(processId);
			responseMessage.setMessage(e.getMessage());
			responseMessage.setRequest(request.getRequest());
		}

		return responseMessage;
	}

	@Override
	public void produceAsynchronous(RequestMessage<T> request) {
		Runnable runnable = new Runnable() {

			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void run() {
				String processId = UUID.randomUUID().toString();
				MessagePostProcessor mpp = new MessagePostProcessor() {

					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setContentType("application/json");
						message.getMessageProperties().setHeader("processId", processId);
						return message;
					}
				};

				if(request.getGroup() == null && request.equals(""))
					jmsTemplate.convertAndSend(request.getTopic(), request.getRequest(), mpp);
				else
					jmsTemplate.convertAndSend(request.getTopic(),request.getGroup(), request.getRequest(), mpp);
			}
		};

		Thread thread = new Thread(runnable);
		thread.run();

	}

	@Override
	public void produceAsynhcronous(String topic, T obj) {
		Runnable runnable = new Runnable() {
			String processId = UUID.randomUUID().toString();

			@Override
			public void run() {
				MessagePostProcessor mpp = new MessagePostProcessor() {

					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setContentType("application/json");
						message.getMessageProperties().setHeader("processId", processId);
						return message;
					}
				};
				jmsTemplate.convertAndSend(topic, obj, mpp);
			}
		};
		Thread thread = new Thread(runnable);
		thread.run();

	}

	@Override
	public void produceAsynhcronous(String topic, String group, T obj) {
		Runnable runnable = new Runnable() {
			String processId = UUID.randomUUID().toString();

			@Override
			public void run() {
				MessagePostProcessor mpp = new MessagePostProcessor() {

					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setContentType("application/json");
						message.getMessageProperties().setHeader("processId", processId);
						return message;
					}
				};
				jmsTemplate.convertAndSend(topic,group, obj, mpp);
			}
		};
		Thread thread = new Thread(runnable);
		thread.run();
	}

}
