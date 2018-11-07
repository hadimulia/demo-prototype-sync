package com.anabatic.cloud.router.persistence.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.anabatic.cloud.router.persistence")
public class PersistenceConfiguration {
private static Logger log = LogManager.getLogger(PersistenceConfiguration.class);
	
	static{
		log.debug("creating bean  from : PersistenceConfiguration" );
	}
}
