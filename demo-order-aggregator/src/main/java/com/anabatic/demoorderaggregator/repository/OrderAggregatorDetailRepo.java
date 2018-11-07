package com.anabatic.demoorderaggregator.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anabatic.demoorderaggregator.entity.OrderAggregatorDetail;

public interface OrderAggregatorDetailRepo extends JpaRepository<OrderAggregatorDetail, Long> {
	@Query(value = "select * from order_aggregator_detail t1 where t1.order_id =:orderId \r\n" + 
			"and exists (\r\n" + 
			"	select * from (\r\n" + 
			"		select row_number() over(partition by subject_type order by created_at desc)  rn ,id,t2.subject_type, t2.created_at\r\n" + 
			"		from order_aggregator_detail t2 where t2.order_id =:orderId \r\n" + 
			"	) t3 where t3.rn=1 and t3.id=t1.id\r\n" + 
			")", nativeQuery=true)
	List<OrderAggregatorDetail> findByOrderId(@Param("orderId") UUID orderId);
}
