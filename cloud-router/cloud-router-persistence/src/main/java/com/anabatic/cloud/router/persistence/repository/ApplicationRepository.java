package com.anabatic.cloud.router.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anabatic.cloud.router.persistence.model.ApplicationModel;

@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationModel, String>{

	ApplicationModel findByServiceIdAndClientId(String serviceId, String clientId);

	
}
