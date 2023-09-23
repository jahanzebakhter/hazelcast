package com.jayzConsulting.spring.cache.api.config;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.HazelcastInstance;
import com.jayzConsulting.spring.cache.api.dao.CustomerRepository;
import com.jayzConsulting.spring.cache.api.model.Customer;
import com.jayzConsulting.spring.cache.api.model.CustomerDependent;
import com.jayzConsulting.spring.cache.api.service.CustomerService;
import com.hazelcast.topic.MessageListener;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;

@Component
public class StartupEventListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupEventListener.class);

    @Autowired
    CustomerService cacheService;

	@Autowired
	private CustomerRepository repository;
	
	 @Autowired
	 CacheManager cacheManager;
	 
	 @Autowired
	 private HazelcastInstance hazelcastInstance;
	 
	 
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent ready) {
       // load cache either from cacheService or DAO layer directly here.
    	
    	/*
    	 * hazelcastInstance.addDistributedObjectListener(
    	 
         		new DistributedObjectListener()
         		
         		{
         		    public void distributedObjectCreated(DistributedObjectEvent event)
         		    {
         		    	 System.out.println("Object Created Event:" + event);
         		    }

         		    
         		    public void distributedObjectDestroyed(DistributedObjectEvent event)
         		    {
         		    	System.out.println("Object Destroyed Event:" + event);
         		    }
         			
         			
         		}
         		
         		);       
         		
         		 */
       
    	
    	if (cacheManager.getCache("customerCache").get("customerCacheInitValue") == null)
    	{
    		System.out.println("Filling up customer cache");	
    		cacheManager.getCache("customerCache").putIfAbsent ("customerCacheInitValue", "cacheFull");
    	
	    	for (Customer cust : repository.findAll()) {
	             cacheManager.getCache("customerCache").putIfAbsent(cust.id, cust);
	         }
    	}
    
    	 
    	
    	
    	ITopic<CustomerDependent> 
    	topic = hazelcastInstance.getReliableTopic("customerDependentCacheTopicWithCustomerIdAsKey");
        topic.addMessageListener(new MessageListenerImpl());
    
        //Cache customerDependentCacheWithCustomerIdAsKey = cacheManager.getCache("customerCache");
   
        
      	topic = hazelcastInstance.getReliableTopic("customerDependentCacheTopic2WithCustomerIdAsKey");
        topic.addMessageListener(new MessageListenerImpl2());
        

       
    
    }
    
    
    private class MessageListenerImpl implements MessageListener<CustomerDependent>
    {
        public void onMessage(Message<CustomerDependent> m) {
        	CustomerDependent custDependent = (CustomerDependent)m.getMessageObject();
        	
            System.out.println("Topic 1 Received: " + custDependent);
            
            Cache customerDependentCache = 
            		StartupEventListener.this.cacheManager.getCache("customerDependentCacheWithCustomerIdAsKey");
            
         	if (customerDependentCache.get(custDependent.getCustomerId()) == null)
        	{
      		
    			HashMap<Integer, CustomerDependent> custDeptHashMap = new HashMap<>();
    			
    			custDeptHashMap.put (Integer.valueOf(custDependent.getId()), custDependent);
    			
    			customerDependentCache.put(custDependent.getCustomerId(), custDeptHashMap);
        		
                	
            } else 
                
                
                {
                	                      	
            	ValueWrapper wrp = customerDependentCache.get(custDependent.getCustomerId());
            	HashMap<Integer, CustomerDependent> custDeptHashMap = ( HashMap<Integer, CustomerDependent>) wrp.get();
            	
                // This operation either adds the dependent to the Hashmap or replaces if the dependent already exists	
            	custDeptHashMap.put(custDependent.getId(),custDependent);
            	customerDependentCache.put(custDependent.getCustomerId(), custDeptHashMap);

                }                       
        		   
         		
          	}
        
        	 
        }
   

    
    private class MessageListenerImpl2 implements MessageListener<CustomerDependent>
    {
        public void onMessage(Message<CustomerDependent> m) {
            System.out.println("Topic 2 Received: " + m.getMessageObject());
        }
    }

}


