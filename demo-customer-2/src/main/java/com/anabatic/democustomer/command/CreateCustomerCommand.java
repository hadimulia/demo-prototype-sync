package com.anabatic.democustomer.command;

import lombok.Data;

@Data
public class CreateCustomerCommand {
	
	private String name;
	private String address;
	private String member;
}
