package com.anabatic.demoproduct.command;

import lombok.Data;

@Data
public class CreateProductCommand {
	
	private String name;
	private Long qty;
	private String desc;
}
