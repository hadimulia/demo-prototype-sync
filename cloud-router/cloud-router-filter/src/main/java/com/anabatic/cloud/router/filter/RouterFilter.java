package com.anabatic.cloud.router.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import com.anabatic.cloud.router.persistence.model.ApplicationModel;
import com.anabatic.cloud.router.persistence.repository.ApplicationRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class RouterFilter extends ZuulFilter {
	private static final String FILTER_TYPE = "route";
	private static final int FILTER_ORDER = 0;
	@Autowired
	private ZuulProperties zuulProp;
    private final DiscoveryClient discoveryClient;
    
    public RouterFilter(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }
	private final Logger log = LogManager.getLogger(RouterFilter.class);

	@Autowired
	private ApplicationRepository applicationRepository;
	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return true;

	}
	
	@Override
	public Object run() throws ZuulException {
		/*List<String> currentServiceIds = new ArrayList<>();*/
		String currentServiceIds = null;
		RequestContext ctx = RequestContext.getCurrentContext();
		
		String clientId = ctx.getRequest().getHeader("client_id");
		
		if(clientId==null) {
            throw new IllegalArgumentException("header client_id null");
		}
		
		for (Entry<String, ZuulRoute> route :  zuulProp.getRoutes().entrySet()) {
			if(ctx.getRequest().getRequestURI().startsWith(route.getValue().getPath().replaceAll("/[*][*]",""))){
				if (route.getKey().endsWith(clientId)) {
					/*currentServiceIds.add(route.getValue().getServiceId());*/
					currentServiceIds = route.getValue().getServiceId();
				}
			}
		}
		
		if(currentServiceIds.isEmpty()) {
            throw new IllegalArgumentException("Couldn't get find service id");
		}
		String currentServiceId = null;
		//Getting client id
		/*for (String serviceId : currentServiceIds) {
			try {
				log.info("getting serviceId "+serviceId +" clientId "+clientId );
				ApplicationModel apps = applicationRepository.findByServiceIdAndClientId(serviceId, clientId);
				if(apps!=null) {
					currentServiceId = apps.getServiceId();
				}	
			} catch (Exception e) {
				log.error(e);
	            throw new IllegalArgumentException("header client_id null");
			}
		}*/
		if(currentServiceIds!=null) { 
			log.info("client id "+ clientId+" service id" + currentServiceIds);
	        RequestContext context = RequestContext.getCurrentContext();
	        List<ServiceInstance> instances = null;
	    	instances = discoveryClient.getInstances(currentServiceIds);
	        
	        try {
	            if (instances != null && instances.size() > 0) {
	            	log.info("Routing into "+instances.get(0).getUri().toURL());
	                context.setRouteHost(instances.get(0).getUri().toURL());
	            } else {
	                throw new IllegalStateException("Target service instance not found!");
	            }
	        } catch (Exception e) {
	            throw new IllegalArgumentException("Couldn't get service URL!", e);
	        }
		
		}
		return null;
	}

}
