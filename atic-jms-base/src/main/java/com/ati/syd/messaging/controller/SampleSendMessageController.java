package com.ati.syd.messaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ati.syd.messaging.produce.MessagingProduce;
import com.ati.syd.messaging.template.RequestMessage;
import com.ati.syd.messaging.template.ResponseMessage;

@Controller
@RequestMapping("/message")
public class SampleSendMessageController {

	@Autowired
	private MessagingProduce<Object> messagingProduce;
	
	@PostMapping("/send")
	public ResponseEntity<ResponseMessage<Object>> sendMessage(@RequestBody RequestMessage<Object> request){
		
		ResponseMessage<Object> response = messagingProduce.produce(request);
		
		
		return new ResponseEntity<ResponseMessage<Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/send/async")
	public ResponseEntity<Object> sendMessageAsynhcronous(@RequestBody RequestMessage<Object> request){
		
		 messagingProduce.produce(request.getTopic(),request.getGroup(), request.getRequest());
		
		
		return new ResponseEntity<Object>(request.getRequest(),HttpStatus.OK);
	}
	
}
