package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Observable;
import com.hazelcast.jet.cdc.CdcSinks;
import com.hazelcast.jet.cdc.ChangeRecord;
import com.hazelcast.jet.cdc.mysql.MySqlCdcSources;
import com.hazelcast.jet.config.JetClientConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.StreamSource;
import com.hazelcast.jet.pipeline.StreamSourceStage;
import com.hazelcast.topic.ITopic;
import com.jayzConsulting.spring.cache.api.model.Customer;
import com.jayzConsulting.spring.cache.api.model.CustomerDependent;

public class JetJob implements Serializable{

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
                .setClusterName("hazelcast-cache-cluster") // not sure how this is used
                
                .setDatabaseWhitelist("cachehazelothers")
                .setTableWhitelist("cachehazelothers.customers", "cachehazelothers.customer_dependent")
                .build();        		
        		
        Pipeline pipeline = Pipeline.create();
        StreamSourceStage <ChangeRecord> sss = pipeline.readFrom(source);
        
        
        
        //sink customer to local customer cache with customer id
        // local customer cache with CdcSinks class
        // only consider customers table
        
        sss.withoutTimestamps()
               // .peek()
                .filter((r) ->  { 
                	return r.table().equalsIgnoreCase("customers");
                			}
                )
                .writeTo
                (		//can also use Sinks.map("customerCache2",
                		CdcSinks.map("customerCache", 
                        (r) -> {

                        	//System.out.println("Customer Cache r has a value: " + r + " ;");
                        	return r.key().toMap().get("id");
                        },
                        (r) -> 
                        {
                        	return r.value().toObject(Customer2.class);
                        	
                        }
                        ));
                
               

        //sink to local customerDependent cache with its own customer_dependent id
        // local customer cache with CdcSinks class
        // only consider customer_dependent table

                
        /*
         *  sss.withoutTimestamps()
        
               // .peek()
                .filter((r) ->  { 
                	return r.table().equalsIgnoreCase("customer_dependent");
                			}
                )
                .writeTo
                (CdcSinks.map("customerDependentCache",
                        (r) -> {

                        	//System.out.println("Customer Dependent Cache r has a value: " + r + " ;");
                        	CustomerDependent cdepend = r.value().toObject(CustomerDependent.class);	
                        	return cdepend.getId();
                        },
                        (r) -> 
                        {
                        	return r.value().toObject(CustomerDependent.class);	
                        }
                        ));

         */


         
         
                
         //sink to local customerDependent cache with customerId, merging with existing HashMap and adding the new object to the same HashMap
         // if customer id is the same and the HashMap already exist in the cache add to it or replace the old customer object
         // local customer dependent cache with Sinks class
         // only consider customer_dependent table
         
        sss.withoutTimestamps()
               // .peek()
                .filter((r) ->  { 
                	return r.table().equalsIgnoreCase("customer_dependent");
                			}
                )
                .writeTo
                (Sinks.mapWithMerging("customerDependentCacheWithCustomerIdAsKey",
                        (r) -> {

                        	//System.out.println("Customer Dependent Cache r has a value: " + r + " ;");
                        	return r.value().toObject(CustomerDependent.class).getCustomerId();
   
                        },
                        
                        (r) -> 
                        {
                        	 
                			HashMap<Integer, CustomerDependent> custDeptHashMap = new HashMap<>();
                			
                			custDeptHashMap.put (Integer.valueOf(r.value().toObject(CustomerDependent.class).getId()),
                					(CustomerDependent)r.value().toObject(CustomerDependent.class) );
                			return  custDeptHashMap;
                        	
                        },
                        
                        (oldValue, newValue) -> 
                        {
                        	Integer i = (Integer)(newValue.keySet().toArray()[0]) ;	                        	
                        	oldValue.put(i, newValue.get(i));	                        	
                        	return oldValue;
                        }                       
                		
                	)
                		
                )  ;
                
                
  
        
 //sink to remote customer cache with customer id
 // remote customer cache with Sinks class
 // only consider customers table

        ClientConfig clientCfg = new ClientConfig();
        clientCfg.setClusterName("hazelCastCacheRestCluster");
        clientCfg.getNetworkConfig().addAddress("192.168.1.111:5801");
        
       
        //sink to remote customer cache
       
        sss.withoutTimestamps()
        //.peek()
        .filter((r) ->  { 
        	return r.table().equalsIgnoreCase("customers");
        			}
        )
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
                ))  ;
                

        

        //sink to remote customerDependent cache with customerId, merging with existing obect Array and adding the new object to the same array
        // if customer id is the same and the array already exist in the cache
        // remote customer dependent cache with Sinks class
        // only consider customer_dependent table
        
        // this gives some class not found exception
      
      /*  
       sss.withoutTimestamps()
              // .peek()
               .filter((r) ->  { 
               	return r.table().equalsIgnoreCase("customer_dependent");
               			}
               )
               .writeTo
               (Sinks.remoteMapWithMerging("customerDependentCacheWithCustomerIdAsKey", clientCfg,
                      
            		   (r) -> {

                       	//System.out.println("Customer Dependent Cache r has a value: " + r + " ;");
                       	return r.value().toObject(CustomerDependent.class).getCustomerId();
  
                       },
                       
                       (r) -> 
                       {
                       	 
               			HashMap<Integer, CustomerDependent> custDeptHashMap = new HashMap<>();
               			
               			custDeptHashMap.put (Integer.valueOf(r.value().toObject(CustomerDependent.class).getId()),
               					(CustomerDependent)r.value().toObject(CustomerDependent.class) );
               			return  custDeptHashMap;
                       	
                       },
                       
                       (oldValue, newValue) -> 
                       {
                       	Integer i = (Integer)(newValue.keySet().toArray()[0]) ;	                        	
                       	oldValue.put(i, newValue.get(i));	                        	
                       	return oldValue;
                       }                       
               		
               		
               		)
               		
               )  ;
               
        */

        
        //sink to remote customerDependent topic 
        //however this never worked. Should be otherwise simple and straightforward
        // only consider customer_dependent table
    
        
        
        sss.withoutTimestamps()
         .filter((r) ->  { 
         	return r.table().equalsIgnoreCase("customer_dependent");
         			}
         )
         .peek()
         .writeTo
         (Sinks.remoteReliableTopic("customerDependentCacheTopic2WithCustomerIdAsKey", clientCfg));
         		 
       
        
        
        
        //Programmatically sink to remote customerDependent topic 
        //receiver to update, merge, parse and store the information correspondingly on its side correctly
        // only consider customer_dependent table
      
               
        JobConfig cfg = new JobConfig().setName("mysql-monitor");
        JetInstance jet = Jet.bootstrappedInstance();
    	JetInstance clientJetinstance = Jet.newJetClient(clientCfg);
  
       //Define a observer on the sink
    	//Manually send the item to the remote topic 
    	//Let the receiver handle the item
        
        Observable<CustomerDependent> observable = jet.newObservable();
        observable.addObserver(e -> { 
        	
        	//System.out.println("Printed from client: " + e);
        	
          	
        	ITopic<CustomerDependent> itopic = clientJetinstance.getReliableTopic("customerDependentCacheTopicWithCustomerIdAsKey");
        	itopic.publish(e);
        	} );
        
        
        sss.withoutTimestamps()
        .filter((r) ->  { 
        	return r.table().equalsIgnoreCase("customer_dependent");
        			}
        )
        //.peek()
        .map (r ->
        {
        	return r.value().toObject(CustomerDependent.class);
        })
        .writeTo ( Sinks.observable(observable));
        
        
        
        
        
        
        try {
        	 jet.newJob(pipeline, cfg).join();

        	} finally {
        	  observable.destroy();
        	}
    }

}