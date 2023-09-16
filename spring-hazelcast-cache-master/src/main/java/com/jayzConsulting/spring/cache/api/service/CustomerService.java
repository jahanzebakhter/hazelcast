package com.jayzConsulting.spring.cache.api.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

/*
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hazelcast.cache.HazelcastCachingProvider;
import com.jayzConsulting.spring.cache.api.dao.CustomerDependentDaoImpl;
import com.jayzConsulting.spring.cache.api.dao.CustomerRepository;
import com.jayzConsulting.spring.cache.api.model.Customer;
import com.jayzConsulting.spring.cache.api.model.CustomerDependent;

@Service
public class CustomerService {

	@Autowired
	private ApplicationContext applicationContext;
	 
	@Autowired
	CacheManager cacheManager;
	 
	@Autowired
	private CustomerRepository repository;

	private CustomerDependentDaoImpl m_customerDependentDao;
	
	@Autowired
	public CustomerService (@Qualifier("CustomerDependent") CustomerDependentDaoImpl p_customerDependentDao)
	{
		m_customerDependentDao = p_customerDependentDao;
		
	}
		
	
	//Customer Cache
	
	@Cacheable(cacheNames = { "customerCache"})
	public List<Customer> getAllCustomers() {
		System.out.println("Customer Cache. Hit DB 1st time in getAllCustomers()");
		return repository.findAll();
	}

	@Cacheable(value = "customerCache", key = "#id", unless = "#result==null")
	public Optional<Customer> getCustomer(int id) {
		System.out.println("Hit DB 1st time in getCustomer(). With Customer Id:" + id);
		return repository.findById(id);
	}

	
	

	@Cacheable(value = "customerDependentCacheWithCustomerIdAsKey", key = "#customerId", unless = "#result==null")
	public Map<Integer, CustomerDependent> 
			getCutomerDependentByCustomerId(int customerId) {
		System.out.println("Hit DB 1st time in getCustomerDependentByCustomerId(). With Customer Id:" + customerId);
		
		CustomerDependent custdep[] = m_customerDependentDao.getCutomerDependentByCustomerId(customerId);
		
		Map<Integer, CustomerDependent>  custDepMapWithId 
		   = Stream.of(custdep)
		        .collect(
		            Collectors.toMap(x -> x.getId(), x -> x));


		
		
		//HashMap<Integer, Map <Integer, CustomerDependent>> custdepMapWithCustomerId = new HashMap<>();
		
		//custdepMapWithCustomerId.put(customerId, custDepMapWithId);
		
		
		return custDepMapWithId;
	}	

	
	
	//Customer Cache 2

	@Cacheable(cacheNames = { "customerCache2"})
	public List<Customer> getAllCustomers2() {
		System.out.println("Cusomter Cache 2. Hit DB 1st time in getAllCustomers2()");
		return repository.findAll();
	}

	@Cacheable(value = "customerCache2", key = "#id", unless = "#result==null")
	public Optional<Customer> getCustomer2(int id) {
		System.out.println("Customers Cache 2. Hit DB 1st time in getCustomer2(). With Customer Id:" + id);
		return repository.findById(id);
	}

	
	
	
	
	
	public void loadAndAnalyzeCustomers() 
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
	
	
	

}
