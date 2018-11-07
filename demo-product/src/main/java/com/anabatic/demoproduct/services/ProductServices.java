package com.anabatic.demoproduct.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.anabatic.demoproduct.command.OrderAggregatorCommand;
import com.anabatic.demoproduct.entity.Product;
import com.anabatic.demoproduct.enums.OrderStatus;
import com.anabatic.demoproduct.enums.PaymentStatus;
import com.anabatic.demoproduct.enums.SubjectType;
import com.anabatic.demoproduct.event.EventUpdateBalanceType;
import com.anabatic.demoproduct.event.FindProductEvent;
import com.anabatic.demoproduct.event.UpdateBalanceCustomerEvent;
import com.anabatic.demoproduct.event.UpdateOrderEvent;
import com.anabatic.demoproduct.event.UpdatePaymentEvent;
import com.anabatic.demoproduct.event.UpdateQtyProductEvent;
import com.anabatic.demoproduct.listener.ProductSink;
import com.anabatic.demoproduct.repo.ProductRepository;
import com.anabatic.demoproduct.util.HibernateProxyUtil;
import com.ati.syd.messaging.produce.MessagingProduce;

@Component
public class ProductServices {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductSink productSink;
	
	@Autowired
	private MessagingProduce<Object> messagingProduce;

	public Product save(Product t) {
		return this.productRepository.save(t);
	}
	
	public void sendToAgregator(FindProductEvent findProductEvent) {
		Product product =  productRepository.getOne(findProductEvent.getProductId());
		
		OrderAggregatorCommand orderAggregatorCommand = new OrderAggregatorCommand();
		orderAggregatorCommand.setOrderId(findProductEvent.getOrderId());
		orderAggregatorCommand.setSubject(HibernateProxyUtil.initializeAndUnproxy(product));
		orderAggregatorCommand.setSubjectType(SubjectType.PRODUCT);
		
		messagingProduce.produce("order.aggregator.update", orderAggregatorCommand);
	}
	
	public void updateProduct(UpdateQtyProductEvent updateQtyProductEvent) {
		Product product = HibernateProxyUtil.initializeAndUnproxy( productRepository.getOne(updateQtyProductEvent.getProductId()));
		
		UpdateOrderEvent order = new UpdateOrderEvent() ;
		order.setOrderId(updateQtyProductEvent.getOrderId());
		
		UpdatePaymentEvent updatePaymentEvent =  new UpdatePaymentEvent();
		updatePaymentEvent.setOrderId(updateQtyProductEvent.getOrderId());
		
		if(product.getQty().compareTo(updateQtyProductEvent.getQty()) < 0) {
			order.setOrderStatus(OrderStatus.FAILURE);
			order.setStatusDesc("Insuficient Quantity Product");
			updatePaymentEvent.setStatus(PaymentStatus.FAILURE);
			updatePaymentEvent.setDesc("Insuficient Quantity Product");
			UpdateBalanceCustomerEvent rollback = new UpdateBalanceCustomerEvent();
			rollback.setCustomerId(updateQtyProductEvent.getCustomerId());
			rollback.setBalance(updateQtyProductEvent.getTotalPayment());
			rollback.setType(EventUpdateBalanceType.CREDIT);
			
			messagingProduce.produce("get.info.from.order", rollback);
		}else {
			product.setQty(product.getQty() - updateQtyProductEvent.getQty());
			productRepository.save(product);
			order.setOrderStatus(OrderStatus.SUCCESSFULL);
			updatePaymentEvent.setStatus(PaymentStatus.SUCCESSFULL);
		}
		
		switch (updateQtyProductEvent.getVersion()) {
		case "v1":
			messagingProduce.produce("update.payment.v1", updatePaymentEvent);
			messagingProduce.produce("order.update.v1", order);
			break;
		case "v2":
			messagingProduce.produce("update.payment.v2", updatePaymentEvent);
			messagingProduce.produce("order.update.v2", order);
			break;
		}
	}
}
