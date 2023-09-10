package com.jayzConsulting.spring.cache.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayzConsulting.spring.cache.api.model.CustomerDependent;

import java.io.Serializable;
import java.util.Objects;




//@Entity
//@Table(name = "cachehazelothers.customer_dependent")
public class CustomerDependent implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//@Id
	//@Column(name = "id", nullable = false)
	@JsonProperty("id")
    public int id;

	
	//@Column(name = "firstName", updatable = true, nullable = false)
    @JsonProperty("First_Name")
    public String firstName;

	//@Column(name = "lastName", updatable = true, nullable = false)
	@JsonProperty("Last_Name")
    public String lastName;

	//@Column(name = "customerId", updatable = true, nullable = false)
	@JsonProperty("Customer_Id")
    public int customerId;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

   public CustomerDependent() 
	   {
		   super();
	   }



    public CustomerDependent(int id, String firstName, String lastName, int customerId) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerId = customerId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, customerId, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomerDependent other = (CustomerDependent) obj;
        return id == other.id
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName)
                && Objects.equals(customerId, other.customerId);
    }

    @Override
    public String toString() {
        return "Customer Dependent {id=" + id + ", firstName=" + firstName + 
        		", lastName=" + lastName + ",  customerId=" + customerId + "}";
    }



}