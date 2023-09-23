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

public class SpringCacheRead {

    public static void main(String[] args) {
    	
    	
    	JetClientConfig config = new JetClientConfig();
    	config.getNetworkConfig().addAddress("192.168.1.50");
    	//, "192.168.1.111"
    	JetInstance instance = Jet.newJetClient(config);
    	
   
        

        System.out.println("Currently there are following customers in the cache:");
        Collection<Object> cust = instance.getMap("customers").values();
        		cust.forEach(c -> System.out.println("\t" + c));

        instance.shutdown();
    }

}