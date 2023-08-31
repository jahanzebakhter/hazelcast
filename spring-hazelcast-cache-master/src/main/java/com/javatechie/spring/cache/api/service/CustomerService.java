package com.javatechie.spring.cache.api.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

/*
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hazelcast.cache.HazelcastCachingProvider;
//import com.hazelcast.cache.HazelcastCachingProvider;
import com.javatechie.spring.cache.api.dao.CustomerRepository;
import com.javatechie.spring.cache.api.model.Customer;

@Service
public class CustomerService {

	 @Autowired
	    private ApplicationContext applicationContext;
	 
	 @Autowired
	 CacheManager cacheManager;
	 
	@Autowired
	private CustomerRepository repository;

	@Cacheable(cacheNames = { "customerCache"})
	public List<Customer> getAllCustomers() {
		System.out.println("Customer Cache. Hit DB 1st time in getAllCustomers()");
		return repository.findAll();
	}

	@Cacheable(value = "customerCache", key = "#id", unless = "#result==null")
	public Optional<Customer> getCustomer(int id) {
		System.out.println("Hit DB 1st time in getCustomer()");
		return repository.findById(id);
	}

	public void loadCache() 
	{

		
		/*
		 * String[] beans = applicationContext.getBeanDefinitionNames();
		 
        for (String bean : beans) {
            System.out.println(bean);
        }
        
        */
        
        
	        Cache custCache =
	        			cacheManager.getCache("customerCache");

	      //  Customer custWr = (Customer)custCache.get(1003).get();
	        
	    	for (Customer cust : repository.findAll()) {
	    		custCache.put(cust.id, cust);
	         }

	
	}
	
	

	@Cacheable(cacheNames = { "customerCache2"})
	public List<Customer> getAllCustomers2() {
		System.out.println("Cusomter Cache 2. Hit DB 1st time in getAllCustomers2()");
		return repository.findAll();
	}

	@Cacheable(value = "customerCache2", key = "#id", unless = "#result==null")
	public Optional<Customer> getCustomer2(int id) {
		System.out.println("Customers Cache 2. Hit DB 1st time in getCustomer()");
		return repository.findById(id);
	}

	

}
