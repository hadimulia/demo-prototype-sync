package com.anabatic.cloud.router.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.anabatic.cloud.router.persistence.configuration.PersistenceConfiguration;

@Configuration
@Import(PersistenceConfiguration.class)
public class RouterFilterConfiguration {
	
	private static Logger log = LogManager.getLogger(PersistenceConfiguration.class);
		
		static{
			log.debug("creating bean  from : PersistenceConfiguration" );
		}

}
