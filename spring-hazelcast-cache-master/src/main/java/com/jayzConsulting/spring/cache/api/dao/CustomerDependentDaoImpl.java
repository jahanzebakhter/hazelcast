package com.jayzConsulting.spring.cache.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.jayzConsulting.spring.cache.api.model.CustomerDependent;


@Component("CustomerDependent")

public class CustomerDependentDaoImpl {

	

    private final NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    CustomerDependentDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    private static final String QUERY_GET_CUSTOMER_DEPENDENT = 
    		
    		"SELECT customer_dependent.id as id, " +
    	    "customer_dependent.First_Name as firstName, " +
    	    "customer_dependent.Last_Name as lastName, " +
    	    "customer_dependent.Customer_Id as customerId " +
    	    "FROM cachehazelothers.customer_dependent " +
    	    "where Customer_Id = :customerId " ;
    
    
    
    
  //Cacheable("InventoryToSend")
    
    public CustomerDependent[] getCutomerDependentByCustomerId(int p_customerId)
	{

        Map<String, Integer> queryParams = new HashMap<>();
        queryParams.put("customerId", p_customerId);
        
        List<CustomerDependent> searchResults = jdbcTemplate.query(
        		QUERY_GET_CUSTOMER_DEPENDENT,
                queryParams,
                new BeanPropertyRowMapper<>(CustomerDependent.class)
        );
 
        
        CustomerDependent[] customerDependentArray = searchResults.stream().toArray(CustomerDependent[]::new);

        return customerDependentArray;	
	}    
    
    
}
