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

import com.javatechie.spring.cache.api.model.Customer;

public class CacheRead {

    public static void main(String[] args) {
    	
    	
    	ClientConfig  clientConfig = new ClientConfig ();
    	
    	//JetClientConfig  jetClientConfig = new JetClientConfig ();

    	//clientConfig.getNetworkConfig().addAddress("192.168.1.111:5801");
    	//clientConfig.getNetworkConfig().addAddress("192.168.1.111:5802");
    	clientConfig.getNetworkConfig().addAddress("192.168.1.50:5820");

    	
    	
    	//JoinConfig joinConfig = jetClientConfig.
    	

    	//jetClientConfig.getMulticastConfig().setMulticastGroup("224.2.2.5");
    	//jetClientConfig.getMulticastConfig().setMulticastPort(54320);    	
    	
    	
    	clientConfig.setClusterName("hazelCastCacheRestCluster");
    	//, "192.168.1.111"
    	
    	
    	
    	
    	JetInstance instance = Jet.newJetClient(clientConfig);
    	
      	
    	
    	
    	

        System.out.println("Currently there are following customers in the cache:");
        Collection<Object> cust =
        		
        		instance.getMap("customerCache").values(); //customers
        		 
        		 	cust.forEach(c -> System.out.println("\t" + c));

        instance.shutdown();
    }

}