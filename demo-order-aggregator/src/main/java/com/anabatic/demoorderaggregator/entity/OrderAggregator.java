package com.anabatic.demoorderaggregator.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="order_aggregator")
public class OrderAggregator {
	
	@Id
	private UUID id;
	private UUID orderId;
	@OneToMany(mappedBy="orderAggregator", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<OrderAggregatorDetail> orderAggregatorDetails;
	private Date createdAt;
}
