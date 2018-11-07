package com.anabatic.demoorder2.sink;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import com.anabatic.demoorder2.events.OrderSyncRequest;

@MessagingGateway
public interface StreamGateway {
	public static final String ENRICH = "enrich";
	public static final String ENRICH_CONFIRM = "enrich-confirm";
	
	@Gateway(requestChannel = ENRICH, replyChannel = GatewayChannel.CREATED_INPUT)
	Message<?> create(OrderSyncRequest payload);
	
	@Gateway(requestChannel = ENRICH_CONFIRM, replyChannel = GatewayChannel.CONFIRM_INPUT)
	Message<?> confirm(OrderSyncRequest payload);
}