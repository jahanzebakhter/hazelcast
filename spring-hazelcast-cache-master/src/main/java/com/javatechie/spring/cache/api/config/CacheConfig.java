package com.javatechie.spring.cache.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;

@Configuration
public class CacheConfig {

	@Bean
	public Config configure() { 
		
		Config cfg = new Config();
		
		cfg.setClusterName("hazelCastCacheRestCluster");
		        
		NetworkConfig network = cfg.getNetworkConfig();
		
		network.setPort(5801);
		network.setPortAutoIncrement(true);
		
		

	
		// Out going port selection for inter machine communication
		// with another hazelcast server
		//List<Integer> listports = List.of(5902, 5903, 5904, 5905, 5906);

		//network.addOutboundPortDefinition("5920-5930");
		
		
		
		
		JoinConfig joinCfg = network.getJoin();
		
		joinCfg.getMulticastConfig().setEnabled(true);
		//joinCfg.getTcpIpConfig().addMember("10.45.67.32").addMember("10.45.67.100").setRequiredMember("192.168.10.100").setEnabled(true);
		
		//network.getInterfaces().setEnabled(true).addInterface("10.45.67.*");
	
		
		joinCfg.getMulticastConfig().setMulticastGroup("224.2.2.5");
		joinCfg.getMulticastConfig().setMulticastPort(54320);
		
		
		EvictionConfig evictionConfig = new EvictionConfig();
		evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
		evictionConfig.setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE).setSize(200);
		//evictionConfig.setMaxSizePolicy(MaxSizePolicy.PER_NODE).setSize(3);
		//evictionConfig.setMaxSizePolicy(MaxSizePolicy.ENTRY_COUNT).setSize(2);
		
		//(new MaxSizeConfig(200,  .MaxSizePolicy.FREE_HEAP_SIZE));
		
		
		cfg = cfg
				//.setInstanceName("hazlecast-cache-spring-boot")
				.setInstanceName("hazelCastCacheRestClusterInstance")
				
				.addMapConfig(new MapConfig().setName("customerCacheConfig")
				.setEvictionConfig(evictionConfig).setTimeToLiveSeconds(20));
		
		return cfg;
		
		
	}

}
