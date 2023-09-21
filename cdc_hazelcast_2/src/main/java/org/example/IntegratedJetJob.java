package org.example;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.cdc.CdcSinks;
import com.hazelcast.jet.cdc.ChangeRecord;
import com.hazelcast.jet.cdc.mysql.MySqlCdcSources;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sink;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.StreamSource;
import com.jayzConsulting.spring.cache.api.model.Customer;

public class IntegratedJetJob {

    public static void main(String[] args) {
    	

        StreamSource<ChangeRecord> source = MySqlCdcSources.mysql("source")
                .setDatabaseAddress("192.168.1.111")
                //.setDatabasePort(4306)
                .setDatabasePort(3306)
                .setDatabaseUser("cacheuser")
                .setDatabasePassword("CachePass_2")
                .setClusterName("hazlecast-cache")
                
                .setDatabaseWhitelist("cachehazelothers")
                .setTableWhitelist("cachehazelothers.customers")
                //.setTableWhitelist("inventory.addresses")                
                .build();
       
        Pipeline pipeline = Pipeline.create();
        pipeline.readFrom(source)
                .withoutTimestamps()
                .peek()
                .writeTo(CdcSinks.map("customers",
                        (r) -> {
                        	//r.key().toMap().get("id");
                        	System.out.println("r has a value: " + r + " ;");
                        	return r.key().toMap().get("id");		
                        },
                        (r) -> 
                        {
                        	return r.value().toObject(Customer.class);
                        	
                        }
                        ));

        
        
             
        JobConfig jobcfg = new JobConfig();
        jobcfg.setName("mysql-monitor");
        
        
             
        /*
        
    	Config cfg = new Config();

        
		NetworkConfig network = cfg.getNetworkConfig();
		
		network.setPort(5911);
		network.setPortAutoIncrement(true);
		
		

	
		// Out going port selection for inter machine communication
		// with another hazelcast server
		//List<Integer> listports = List.of(5902, 5903, 5904, 5905, 5906);

		network.addOutboundPortDefinition("5912-5916");
		
		
		
		
		JoinConfig joinCfg = network.getJoin();
		
		joinCfg.getMulticastConfig().setEnabled(true);
		//joinCfg.getTcpIpConfig().addMember("10.45.67.32").addMember("10.45.67.100").setRequiredMember("192.168.10.100").setEnabled(true);
		
		//network.getInterfaces().setEnabled(true).addInterface("10.45.67.*");

		joinCfg.getMulticastConfig().setMulticastGroup("224.2.2.5");
		joinCfg.getMulticastConfig().setMulticastPort(5801);        
        
        */
        
        
        
        JetConfig jetConfig = new JetConfig();
        jetConfig.getInstanceConfig().setCooperativeThreadCount(4);
        jetConfig.configureHazelcast(network -> {
        	JoinConfig joinCfg = network.getNetworkConfig().getJoin(); 

        	joinCfg.getMulticastConfig().setEnabled(true);
    		//joinCfg.getTcpIpConfig().addMember("10.45.67.32").addMember("10.45.67.100").setRequiredMember("192.168.10.100").setEnabled(true);
    		
    		//network.getInterfaces().setEnabled(true).addInterface("10.45.67.*");

    		joinCfg.getMulticastConfig().setMulticastGroup("224.2.2.5");
    		joinCfg.getMulticastConfig().setMulticastPort(54328);        	
        	
    		network.setClusterName("hazlecast-cache-jet");
    		network.getNetworkConfig().setPort(5911);
    		network.getNetworkConfig().setPortAutoIncrement(true);

    		//network.getNetworkConfig().addOutboundPortDefinition("5915-5919");

      });
        
        //JetInstance jetInst = Jet.newJetInstance(jetConfig);

        JetInstance jetInst = Jet.newJetInstance();
        
        //JetInstance jetInst = Jet.bootstrappedInstance();
        
        JetConfig jcnfg = jetInst.getConfig();
        
        
        jetInst.newJob(pipeline, jobcfg);
        
    }

}