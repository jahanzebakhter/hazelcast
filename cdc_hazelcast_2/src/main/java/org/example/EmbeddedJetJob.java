package org.example;


import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;

import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;

import com.hazelcast.jet.pipeline.test.TestSources;

public class EmbeddedJetJob {

	public static void main(String[] args) {
		  Pipeline p = Pipeline.create();
		  p.readFrom(TestSources.itemStream(10))
		   .withoutTimestamps()
		   .filter(event -> event.sequence() % 3 == 0)
		   .setName("allow every third event numbers")
		   .writeTo(Sinks .logger());
	
	
	
		  //JetInstance jet = Jet.newJetInstance(); // replaced by below
		  
		  JetInstance jet = Jet.bootstrappedInstance();
		  jet.newJob(p).join();	
		  //jet.newJob(p);	
		  
		  System.out.println("Main Process Done");
        
   
    }

}