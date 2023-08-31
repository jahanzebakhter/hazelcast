package com.javatechie.spring.cache.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.cache.api.model.User;
import com.javatechie.spring.cache.api.service.UserService;

@RestController
@RequestMapping("/cache-api")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/getAllUsers")
	public User[]  getAllUsers() {
		
		List <User> usr = service.getUsers();
		User[] usrArray = new User[usr.size()];
		usrArray = usr.toArray(usrArray);
		
		System.out.println(Arrays.toString (usrArray));
		return usrArray;
	}


	@GetMapping("/getUserById/{id}")
	public Optional<User> getUser(@PathVariable int id) {
		return service.getUser(id);
	}

	
	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable int id) {
		return service.delete(id);

	}

}