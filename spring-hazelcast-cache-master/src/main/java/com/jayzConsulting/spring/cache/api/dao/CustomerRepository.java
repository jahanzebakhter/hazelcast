package com.jayzConsulting.spring.cache.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jayzConsulting.spring.cache.api.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
