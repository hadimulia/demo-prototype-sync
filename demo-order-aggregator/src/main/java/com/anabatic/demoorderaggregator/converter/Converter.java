package com.anabatic.demoorderaggregator.converter;

import java.util.List;

import com.anabatic.demoorderaggregator.dto.OrderInfoDto;
import com.anabatic.demoorderaggregator.entity.OrderAggregator;
import com.anabatic.demoorderaggregator.entity.OrderAggregatorDetail;

public interface Converter {
	OrderInfoDto toDto(List<OrderAggregatorDetail> orderAggregatorDetail);
	String checkStatus(List<OrderAggregatorDetail> orderAggregatorDetail);
}
