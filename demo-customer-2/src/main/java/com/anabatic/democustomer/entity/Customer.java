package com.anabatic.democustomer.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	private UUID id;
	@Column(name="name")
	private String name;
	@Column(name="balance")
	private BigDecimal balance;
	@Column(name="address")
	private String address;
	@Column(name="member")
	private String member;
	
	
	
}
