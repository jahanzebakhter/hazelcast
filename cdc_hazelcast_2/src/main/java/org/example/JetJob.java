package org.example;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.cdc.CdcSinks;
import com.hazelcast.jet.cdc.ChangeRecord;
import com.hazelcast.jet.cdc.mysql.MySqlCdcSources;
import com.hazelcast.jet.config.JetClientConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.StreamSource;
import com.hazelcast.jet.pipeline.StreamSourceStage;
import com.javatechie.spring.cache.api.model.Customer;

public class JetJob {

    public static void main(String[] args) {
        StreamSource<ChangeRecord> source = MySqlCdcSources.mysql("source")
               /* .setDatabaseAddress("127.0.0.1")
                .setDatabasePort(3306)
                .setDatabaseUser("debezium")
                .setDatabasePassword("dbz")
                .setClusterName("dbserver1")
                .setDatabaseWhitelist("inventory")
                .setTableWhitelist("inventory.customers")
                .build();*/

                .setDatabaseAddress("192.168.1.111")
                //.setDatabasePort(4306)
                .setDatabasePort(3306)
                .setDatabaseUser("cacheuser")
                .setDatabasePassword("CachePass_2")
                .setClusterName("hazlecast-cache")
                
                .setDatabaseWhitelist("cachehazelothers")
                .setTableWhitelist("cachehazelothers.customers")
                .build();        		
        		
        Pipeline pipeline = Pipeline.create();
        StreamSourceStage <ChangeRecord> sss = pipeline.readFrom(source);
        //sink to local customer cache
                sss.withoutTimestamps()
                .peek()
                .writeTo
                (CdcSinks.map("customerCache",
                        (r) -> {

                        	System.out.println("Customer Cache 2 r has a value: " + r + " ;");
                        	return r.key().toMap().get("id");
                        },
                        (r) -> 
                        {
                        	return r.value().toObject(Customer2.class).toString();
                        	
                        }
                        ));
                //sink to local customer cache2
                sss.withoutTimestamps()
                .peek()
                .writeTo
                (Sinks.map("customerCache3",
                        (r) -> {

                        	System.out.println("Customer Cache 3 r has a value: " + r + " ;");
                        	return r.key().toMap().get("id");
                        },
                        (r) -> 
                        {
                        	return r.value().toObject(Customer2.class).toString();
                        	
                        }
                        ))
                
                
                ;

                

                ClientConfig clientCfg = new ClientConfig();
                clientCfg.setClusterName("hazelCastCacheRestCluster");
                clientCfg.getNetworkConfig().addAddress("192.168.1.111:5801");
                
                //sink to remote customer cache
                sss.withoutTimestamps()
                .peek()
                .writeTo
                (Sinks.remoteMap("customerCache2", clientCfg,
                		
                        (r) -> {

                        	//System.out.println("Customer Cache 3 r has a value: " + r + " ;");
                        	return r.key().toMap().get("id");
                        },
                        (r) -> 
                        {
                        	return r.value().toObject(Customer.class);
                        	
                        }
                        ))
                
                
                ;
                
                //(CdcSinks.map("customers",
 //                       r -> r.key().toMap().get("id"),
//                        r -> r.value().toObject(Customer.class).toString()));

        JobConfig cfg = new JobConfig().setName("mysql-monitor");
        Jet.bootstrappedInstance().newJob(pipeline, cfg);
    }

}