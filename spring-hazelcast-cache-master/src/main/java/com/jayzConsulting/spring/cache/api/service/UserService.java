package com.jayzConsulting.spring.cache.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jayzConsulting.spring.cache.api.dao.UserRepository;
import com.jayzConsulting.spring.cache.api.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Cacheable(cacheNames = { "userCache" })
	public List<User> getUsers() {
		System.out.println("Hit DB 1st time in getUsers()");
		return repository.findAll();
	}

	@Cacheable(value = "userCache", key = "#id", unless = "#result==null")
	public Optional<User> getUser(int id) {
		System.out.println("Hit DB 1st time in getUser()");
		return repository.findById(id);
	}

	@CacheEvict(value = "userCache")
	public String delete(int id) {
		repository.deleteById(id);
		return "User deleted with id " + id;
	}

}
