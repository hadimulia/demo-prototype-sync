package com.anabatic.demoproduct.entity;

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
@Table(name="product")
public class Product {
	
	@Id
	private UUID id;
	@Column(name="name")
	private String name;
	@Column(name="qty")
	private Long qty;
	@Column(name="description")
	private String description;
	
	
	
}
