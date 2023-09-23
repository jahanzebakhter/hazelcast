package com.jayzConsulting.spring.cache.api.config;



import java.util.logging.Logger;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class StartupApplicationListenerAndRunnerExample implements 
  ApplicationListener<ContextRefreshedEvent>, CommandLineRunner, ApplicationRunner {

    private static final Logger LOG 
      = Logger.getLogger(StartupApplicationListenerAndRunnerExample.class.getCanonicalName());

   

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
    	  System.out.println("In Application Context Refresh Event");
    	  
    	  
    	if (event.getApplicationContext().getParent() == null) {
            // root application context
    		System.out.println("No parent of the Application Context Refresh Event");
        }
    }
    
    public void run(String...args) throws Exception {
    	System.out.println("In Run method CommandLineRunner");
       
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
      System.out.println("Application started with option names : {}"+ 
          args.getOptionNames());

    }
}