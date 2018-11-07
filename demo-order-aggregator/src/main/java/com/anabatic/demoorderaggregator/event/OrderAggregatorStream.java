package com.anabatic.demoorderaggregator.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.anabatic.demoorderaggregator.command.OrderAggregatorCommand;
import com.anabatic.demoorderaggregator.command.OrderSyncRequest;
import com.anabatic.demoorderaggregator.dto.OrderInfoDto;
import com.anabatic.demoorderaggregator.service.OrderAggregatorService;
import com.anabatic.demoorderaggregator.sink.OrderAggregatorSink;

@Configuration
@EnableBinding(OrderAggregatorSink.class)
public class OrderAggregatorStream {
	private static final Logger LOG = LogManager.getLogger(OrderAggregatorStream.class);

	@Autowired
	private OrderAggregatorService orderAggregatorService;

	@Autowired
	private CloudStreamChannels channels;

	@KafkaListener(topics="order.aggregator.update")
	public void apply(Message<OrderAggregatorCommand> orderAggregatorCommand) {
		LOG.info("Getting update for Aggregator ---- " + orderAggregatorCommand.getPayload().getOrderId() + " :: "
				+ orderAggregatorCommand.getPayload().getSubject());
		OrderAggregatorCommand orderCommand = orderAggregatorCommand.getPayload();
		if (orderCommand != null) {
			try {
				orderAggregatorService.save(orderAggregatorCommand.getPayload());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@KafkaListener(topics="order.created.sync.request")
	public void created(Message<OrderSyncRequest> request) throws InterruptedException {
		OrderSyncRequest confirmOrder = request.getPayload();
		OrderInfoDto orderInfoDto = orderAggregatorService.orderInfoSync(confirmOrder.getOrderId());
		channels.createdOutput().send(MessageBuilder.withPayload(orderInfoDto)
							.copyHeaders(request.getHeaders()).build());
	}
	
	@KafkaListener(topics="order.confirm.sync.request")
	public void confirm(Message<OrderSyncRequest> request) throws InterruptedException {
		OrderSyncRequest confirmOrder = request.getPayload();
		OrderInfoDto orderInfoDto = orderAggregatorService.orderInfoConfirm(confirmOrder.getOrderId());
		channels.confirmOutput().send(MessageBuilder.withPayload(orderInfoDto)
							.copyHeaders(request.getHeaders()).build());
	}
}
