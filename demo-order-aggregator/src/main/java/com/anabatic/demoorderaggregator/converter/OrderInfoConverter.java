package com.anabatic.demoorderaggregator.converter;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.anabatic.demoorderaggregator.dto.OrderInfoDto;
import com.anabatic.demoorderaggregator.dto.PaymentInfoDto;
import com.anabatic.demoorderaggregator.entity.OrderAggregatorDetail;
import com.anabatic.demoorderaggregator.event.SubjectType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@Component
public class OrderInfoConverter implements Converter {

	Gson gson = new Gson();

	@Override
	public OrderInfoDto toDto(List<OrderAggregatorDetail> orderAggregatorDetail) {
		OrderInfoDto orderInfoDto = null;
		if (orderAggregatorDetail != null && !orderAggregatorDetail.isEmpty()) {
			if (orderAggregatorDetail.size() == 4) {
				JsonElement jsonElement = gson.toJsonTree(toObject(orderAggregatorDetail));
				JsonObject jsonObject = (JsonObject) jsonElement;
				Map<String, Object> payment = gson.fromJson(jsonObject.get("PAYMENT"),
						new TypeToken<HashMap<String, Object>>() {
						}.getType());
				Map<String, Object> order = gson.fromJson(jsonObject.get("ORDER"),
						new TypeToken<HashMap<String, Object>>() {
						}.getType());
				Map<String, Object> customer = gson.fromJson(jsonObject.get("CUSTOMER"),
						new TypeToken<HashMap<String, Object>>() {
						}.getType());
				Map<String, Object> product = gson.fromJson(jsonObject.get("PRODUCT"),
						new TypeToken<HashMap<String, Object>>() {
						}.getType());

				PaymentInfoDto paymentInfo = PaymentInfoDto.builder()
						.statusPayment(payment.get("paymentStatus").toString())
						.paymentType(
								order.get("paymentType") != null ? order.get("paymentType").toString() : "Transfer")
						.bank(order.get("paymentType") != null
								? !order.get("paymentType").toString().equalsIgnoreCase("WALLET") ? "Bank IKI" : null
								: null)
						.namaRekening(order.get("paymentType") != null
								? !order.get("paymentType").toString().equalsIgnoreCase("WALLET") ? "M. HAKIM" : null
								: "M. HAKIM")
						.noRekening(order.get("paymentType") != null
								? !order.get("paymentType").toString().equalsIgnoreCase("WALLET") ? "133-133-133-133"
										: null
								: "133-133-133-133")
						.build();

				orderInfoDto = OrderInfoDto.builder().orderId(order.get("orderId").toString())
						.customerName(customer.get("name").toString()).productName(product.get("name").toString())
						.qty(order.get("qty").toString()).statusOrder(order.get("orderStatus").toString())
						.statusDesc(order.get("statusDesc") != null ? order.get("statusDesc").toString() : null)
						.totalPayment(new BigDecimal(payment.get("totalPayment").toString())).paymentInfo(paymentInfo)
						.build();
			}
		}
		return orderInfoDto;
	}

	@Override
	public String checkStatus(List<OrderAggregatorDetail> orderAggregatorDetail) {
		if (orderAggregatorDetail != null && !orderAggregatorDetail.isEmpty()) {
			if (orderAggregatorDetail.size() == 4) {
				JsonElement jsonElement = gson.toJsonTree(toObject(orderAggregatorDetail));
				JsonObject jsonObject = (JsonObject) jsonElement;
				Map<String, Object> order = gson.fromJson(jsonObject.get("ORDER"),
						new TypeToken<HashMap<String, Object>>() {
						}.getType());
				return order.get("orderStatus").toString();
			}
		}
		return null;
	}

	private Map<SubjectType, Object> toObject(List<OrderAggregatorDetail> orderAggregatorDetail) {
		Map<SubjectType, Object> objects = new HashMap<>();
		for (OrderAggregatorDetail orderAggregatorDetail2 : orderAggregatorDetail) {
			Object result = new Object();
			try {
				ByteArrayInputStream baip = new ByteArrayInputStream(orderAggregatorDetail2.getSubject());
				ObjectInputStream ois = new ObjectInputStream(baip);
				result = ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
			objects.put(orderAggregatorDetail2.getSubjectType(), result);
		}
		return objects;
	}
}
