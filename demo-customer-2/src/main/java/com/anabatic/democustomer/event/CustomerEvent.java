package com.anabatic.democustomer.event;

import com.anabatic.democustomer.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerEvent {
	private Customer subject;
	private EventType eventType;
}
