package org.example;

import java.util.Collection;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetClientConfig;
import com.hazelcast.topic.ITopic;
import com.jayzConsulting.spring.cache.api.model.Customer;

public class CacheReadFromHazelCastJet {

    public static void main(String[] args) {
    	
    	
    	ClientConfig  clientConfig = new ClientConfig ();
    	
    	//JetClientConfig  jetClientConfig = new JetClientConfig ();

    	//connection to jet spring rest cluster
    	//clientConfig.getNetworkConfig().addAddress("192.168.1.111:5801");
    	//clientConfig.getNetworkConfig().addAddress("192.168.1.111:5802");
    	//clientConfig.getNetworkConfig().addAddress("192.168.1.50:5820");

    	//connection to jet environment
    	clientConfig.getNetworkConfig().addAddress("192.168.1.111:5950");

    	
    	//JoinConfig joinConfig = jetClientConfig.
    	

    	//jetClientConfig.getMulticastConfig().setMulticastGroup("224.2.2.5");
    	//jetClientConfig.getMulticastConfig().setMulticastPort(54320);    	
    	
    	//use this to get data from the rest server hazelCastCacheRestCluster
    	// use this for jet job jetTestJobRuntime
    	// not sure why jet job has a separate set cluster hazelcast-cache-cluster
    	clientConfig.setClusterName("jetTestRuntime");

    	//, "192.168.1.111"
    	
    	
    	
    	
    	JetInstance instance = Jet.newJetClient(clientConfig);
    	
      	
    	//ITopic<String> itopic = instance.getReliableTopic("customerDependentCacheTopicWithCustomerIdAsKey");
    	//itopic.publish("Hello");
    	
    	

        System.out.println("Currently there are following customers in the cache:");
        Collection<Object> cust =
        		
        		instance.getMap("customerCache").values(); //customers
        		 
        		 	cust.forEach(c -> System.out.println("\t" + c));

        		 	
        		 	
        System.out.println("Currently there are following customers_dependent in the customer_dependent_cache:");
         
        cust = instance.getMap("customerDependentCache").values(); //customers
	 
	 	cust.forEach(c -> System.out.println("\t" + c));

        
	 	System.out.println("Currently there are following customers_dependent in the customer_dependent_cache:");

	 	cust = instance.getMap("customerDependentCacheWithCustomerIdAsKey").values(); //customers
		 
	 	cust.forEach(c -> System.out.println("\t" + c));

	 			 	
        		 	
        instance.shutdown();
    }

}