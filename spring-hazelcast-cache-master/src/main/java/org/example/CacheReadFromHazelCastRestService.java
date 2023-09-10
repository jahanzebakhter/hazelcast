package org.example;

import java.util.Collection;
import java.util.concurrent.CompletionStage;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.hazelcast.cache.ICache;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;

import com.hazelcast.topic.ITopic;
import com.jayzConsulting.spring.cache.api.model.Customer;

public class CacheReadFromHazelCastRestService {

    public static void main(String[] args) {
    	
    	
    	ClientConfig  clientConfig = new ClientConfig ();
    	
 
    	//connection to jet spring rest cluster
    	clientConfig.getNetworkConfig().addAddress("192.168.1.111:5801");
    	//clientConfig.getNetworkConfig().addAddress("192.168.1.111:5802");
    	//clientConfig.getNetworkConfig().addAddress("192.168.1.50:5820");

    	
    	//JoinConfig joinConfig = clientConfig.setConfigPatternMatcher()
    	

    	//clientConfig.getMulticastConfig().setMulticastGroup("224.2.2.5");
    	//clientConfig.getMulticastConfig().setMulticastPort(54320);    	
    	
    	//use this to get data from the rest server hazelCastCacheRestCluster
    	// not sure why jet job has a separate set cluster hazelcast-cache-cluster
    	clientConfig.setClusterName("hazelCastCacheRestCluster");
    	
    	clientConfig.setInstanceName("hazelCastCacheRestClusterInstance");

    	//, "192.168.1.111"
    	
    	
    	
    	
    	HazelcastInstance instance = HazelcastClient.newHazelcastClient(clientConfig);
    	
      	
    	//ITopic<String> itopic = instance.getReliableTopic("customerDependentCacheTopicWithCustomerIdAsKey");
    	//itopic.publish("Hello");
    	
    	
        System.out.println("Currently there are following customers in the cache:");
        
    	Collection <Object> custCache=	instance.getMap("customerCache").values();
    	    	
        custCache.forEach(c -> System.out.println("\t" + c));  		 	
        		 	
        
        System.out.println("Currently there are following customers2 in the cache2:");

    	custCache=	instance.getMap("customerCache2").values();
    	
        custCache.forEach(c -> System.out.println("\t" + c));  		
        
        
        
       // System.out.println("Currently there are following customers_dependent in the customer_dependent_cache:");
         
        //custCache = instance.getMap("customerDependentCache").values(); //customers
	 
        //custCache.forEach(c -> System.out.println("\t" + c));

        
	 	System.out.println("Currently there are following customers_dependent in the customer_dependent_cache:");

	 	custCache = instance.getMap("customerDependentCacheWithCustomerIdAsKey").values(); //customers
		 
	 	custCache.forEach(c -> System.out.println("\t" + c));
        		 	
        instance.shutdown();
    }

}