package com.blairi.myproject.myproject.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		
		Optional<User> userFound = userRepository.findById(id);
		
		if(userFound.isEmpty()) throw new RuntimeException("User not found");
		
		return userFound.get();
	}
	
	public User create(User user) {
		// TODO: Hash the password
		return userRepository.save(user);
	}
	
}
