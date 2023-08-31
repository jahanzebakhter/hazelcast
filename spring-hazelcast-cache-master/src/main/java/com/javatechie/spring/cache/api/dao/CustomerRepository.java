package com.javatechie.spring.cache.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.spring.cache.api.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
