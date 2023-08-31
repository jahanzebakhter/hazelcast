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
import com.hazelcast.jet.pipeline.StreamSource;

public class OldJetJob {

    public static void main(String[] args) {
        StreamSource<ChangeRecord> source = MySqlCdcSources.mysql("source")
                .setDatabaseAddress("192.168.1.111")
                //.setDatabasePort(4306)
                .setDatabasePort(3306)
                .setDatabaseUser("debezium")
                .setDatabasePassword("dbz")
                .setClusterName("dbserver1")
                
                .setDatabaseWhitelist("inventory")
                .setTableWhitelist("inventory.customers")
                //.setTableWhitelist("inventory.addresses")                
                .build();
       
        Pipeline pipeline = Pipeline.create();
        pipeline.readFrom(source)
                .withoutTimestamps()
                .peek()
                .writeTo(CdcSinks.map("customers",
                        (r) -> {
                        	r.key().toMap().get("id");
                        	System.out.println("r has a value: " + r + " ;");
                        	return r;		
                        },
                        (r) -> 
                        {
                        	r.value().toObject(Customer2.class).toString();
                        	return r;
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
        
        
        
        
        
        
        JetInstance jetInst = Jet.bootstrappedInstance();
        
        JetConfig jcnfg = jetInst.getConfig();
        
        jetInst.newJob(pipeline, jobcfg);
        
        
        
    }

}