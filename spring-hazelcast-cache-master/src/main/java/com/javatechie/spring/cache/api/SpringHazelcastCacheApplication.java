package com.javatechie.spring.cache.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringHazelcastCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringHazelcastCacheApplication.class, args);
	}
}
