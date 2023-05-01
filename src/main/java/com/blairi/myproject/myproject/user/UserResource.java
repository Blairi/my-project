package com.blairi.myproject.myproject.user;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{id}")
	public User retrieveUser(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
		
		User savedUser = userService.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<Object> updateUser(
			@PathVariable Long id,
			@RequestBody @Valid User userUpdated) {
		
		User userFound = userService.findById(id);
		
		userFound.setEmail( userUpdated.getEmail() );
		userFound.setName( userUpdated.getName() );
		userFound.setPassword( userUpdated.getPassword() );
		
		userService.save(userFound);
		
		return ResponseEntity.ok().build();
	}
	
}
