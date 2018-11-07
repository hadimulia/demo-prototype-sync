package com.ati.syd.messaging.produce;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.ati.syd.messaging.enums.MessagingProviderEnum;
import com.ati.syd.messaging.template.RequestMessage;
import com.ati.syd.messaging.template.ResponseMessage;

@Service
@Profile(MessagingProviderEnum.KAFKA)
public class MessagingProduceKafkaImpl<T> extends MessagingProduceImpl<T> {



	@Autowired
	protected KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public T produce(String topic, T obj) {
		String processId = UUID.randomUUID().toString();
		Message<T> message = MessageBuilder.withPayload(obj)
									.setHeader(KafkaHeaders.TOPIC, topic)
									.setHeader("processId", processId).build();
		kafkaTemplate.send(message);
		return obj;
	}

	@Override
	public T produce(String topic, String group, T obj) {
		String processId = UUID.randomUUID().toString();
		Message<T> message = MessageBuilder.withPayload(obj)
								 .setHeader(KafkaHeaders.TOPIC, topic)
								 .setHeader(KafkaHeaders.MESSAGE_KEY, group).setHeader("processId", processId).build();
		kafkaTemplate.send(message);
		return obj;
	}

	@Override
	public ResponseMessage<T> produce(RequestMessage<T> request) {
		String processId = UUID.randomUUID().toString();
		ResponseMessage<T> responseMessage = new ResponseMessage<T>();

		try {
			Message<T> message = MessageBuilder.withPayload(request.getRequest())
					.setHeader(KafkaHeaders.TOPIC, request.getTopic())
					.setHeader(KafkaHeaders.MESSAGE_KEY, request.getGroup()).setHeader("processId", processId).build();
			kafkaTemplate.send(message);
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

			@Override
			public void run() {
				String processId = UUID.randomUUID().toString();
				Message<T> message = MessageBuilder.withPayload(request.getRequest())
						.setHeader(KafkaHeaders.TOPIC, request.getTopic())
						.setHeader(KafkaHeaders.MESSAGE_KEY, request.getGroup()).setHeader("processId", processId)
						.build();
				kafkaTemplate.send(message);
			}
		};

		Thread thread = new Thread(runnable);
		thread.run();

	}

	@Override
	public void produceAsynhcronous(String topic, T obj) {
		Runnable runnable = new Runnable() {
			

			@Override
			public void run() {
				String processId = UUID.randomUUID().toString();
				Message<T> message = MessageBuilder.withPayload(obj)
											.setHeader(KafkaHeaders.TOPIC, topic)
											.setHeader("processId", processId).build();
				kafkaTemplate.send(message);
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

				Message<T> message = MessageBuilder.withPayload(obj)
						 .setHeader(KafkaHeaders.TOPIC, topic)
						 .setHeader(KafkaHeaders.MESSAGE_KEY, group).setHeader("processId", processId).build();
				kafkaTemplate.send(message);
			}
		};
		Thread thread = new Thread(runnable);
		thread.run();
	}	
	
}
