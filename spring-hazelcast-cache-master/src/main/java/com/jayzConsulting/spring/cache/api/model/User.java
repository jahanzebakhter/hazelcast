package com.jayzConsulting.spring.cache.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user" )
public class User implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = -6912201477750422475L;
	@Id
	private int id;
	private String name;
	private String address;
	
	public String toString()
	{
		return ("id: " + id + ", name:" + name + ", address:" + address);
	}

	public int getId() {
		return id;
	}

	public void setId(int p_id) {
		id = p_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String p_name) {
		name = p_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String p_address) {
		address = p_address;
	}
}
   