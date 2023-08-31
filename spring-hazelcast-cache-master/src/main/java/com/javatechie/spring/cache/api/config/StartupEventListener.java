package com.javatechie.spring.cache.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.javatechie.spring.cache.api.dao.CustomerRepository;
import com.javatechie.spring.cache.api.model.Customer;
import com.javatechie.spring.cache.api.service.CustomerService;

@Component
public class StartupEventListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupEventListener.class);

    @Autowired
    CustomerService cacheService;

	@Autowired
	private CustomerRepository repository;
	
	 @Autowired
	 CacheManager cacheManager;
	 
    
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent ready) {
       // load cache either from cacheService or DAO layer directly here.
    	
    	if (cacheManager.getCache("customerCache").get("customerCache") == null)
    	{
    		System.out.println("Filling up cache");	
    		cacheManager.getCache("customerCache").putIfAbsent ("customerCache", "cacheFull");
    	
	    	for (Customer cust : repository.findAll()) {
	             cacheManager.getCache("customerCache").putIfAbsent(cust.id, cust);
	         }
    	}
    }
}