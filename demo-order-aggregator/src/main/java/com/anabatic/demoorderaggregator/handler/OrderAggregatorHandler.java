package com.anabatic.demoorderaggregator.handler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anabatic.demoorderaggregator.command.OrderInfoCommand;
import com.anabatic.demoorderaggregator.dto.OrderInfoDto;
import com.anabatic.demoorderaggregator.service.OrderAggregatorService;

@RestController
public class OrderAggregatorHandler {

	@Autowired
	private OrderAggregatorService orderAggregatorService;

	@PostMapping("/view-order")
	public ResponseEntity<?> viewOrder(@RequestBody OrderInfoCommand orderInfoCommand) {
		OrderInfoDto orderInfoDto = orderAggregatorService.orderInfo(orderInfoCommand.getOrderId());
		if (orderInfoDto != null) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orderInfoDto);
		}else {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
					.body("OrderId not found");
		}
	}
}
