package com.anabatic.demoproduct.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CloudStreamChannels {
	String TO_UPPERCASE_REPLY = "to-uppercase-reply";
    String TO_UPPERCASE_REQUEST = "to-uppercase-request";

    @Output(TO_UPPERCASE_REPLY)
    MessageChannel toUppercaseReply();

    @Input(TO_UPPERCASE_REQUEST)
    SubscribableChannel toUppercaseRequest();
}
