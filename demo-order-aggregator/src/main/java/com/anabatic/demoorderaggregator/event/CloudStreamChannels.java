package com.anabatic.demoorderaggregator.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface CloudStreamChannels {
	String CREATED_INPUT = "order-created-sync-input";
    String CREATED_OUTPUT = "order-created-sync-output";
    String CONFIRM_INPUT = "order-confirm-sync-input";
    String CONFIRM_OUTPUT = "order-confirm-sync-output";
    
    @Input(CREATED_INPUT)
    SubscribableChannel createdInput();

    @Output(CREATED_OUTPUT)
    SubscribableChannel createdOutput();
    
    @Input(CONFIRM_INPUT)
    SubscribableChannel confirmInput();

    @Output(CONFIRM_OUTPUT)
    SubscribableChannel confirmOutput();
}
