package com.javatechie.spring.cache.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.cache.api.model.Customer;
import com.javatechie.spring.cache.api.service.CustomerService;

@RestController
@RequestMapping("/cache-api")
public class CustomerController {

	@Autowired
	private CustomerService service;

	@GetMapping("/getAllCustomers")
	public Customer[]  getAllCustomers() {
		
		List <Customer> cust = service.getAllCustomers();
		Customer[] custArray = new Customer[cust.size()];
		custArray = cust.toArray(custArray);
		
		System.out.println(Arrays.toString (custArray));
		return custArray;
	}


	@GetMapping("/getCustomerById/{id}")
	public Optional<Customer> getCustomer(@PathVariable int id) {
		return service.getCustomer(id);
	}


	@GetMapping("/loadCustomers")
	public void loadCustomers() 
	{
		 service.loadCache();
	}

	
	
	
	
	@GetMapping("/getAllCustomers2")
	public Customer[]  getAllCustomers2() {
		
		List <Customer> cust = service.getAllCustomers2();
		Customer[] custArray = new Customer[cust.size()];
		custArray = cust.toArray(custArray);
		
		//System.out.println(Arrays.toString (custArray));
		return custArray;
	}


	@GetMapping("/getCustomerById2/{id}")
	public Optional<Customer> getCustomer2(@PathVariable int id) {
		return service.getCustomer2(id);
	}

}
