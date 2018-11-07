package com.anabatic.demoorderaggregator.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.anabatic.demoorderaggregator.event.SubjectType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="order_aggregator_detail")
public class OrderAggregatorDetail {
	
	@Id
	@GeneratedValue
	private Long id;
	private UUID orderId;
	@Enumerated(EnumType.STRING)
	private SubjectType subjectType;
	private byte[] subject;
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="order_aggregator")
	private OrderAggregator orderAggregator;
	private Date createdAt;
}
