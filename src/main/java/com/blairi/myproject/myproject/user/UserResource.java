package com.blairi.myproject.myproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{id}")
	public User retrieveUser(@PathVariable Long id) {
		return userService.findById(id);
	}
	
}
