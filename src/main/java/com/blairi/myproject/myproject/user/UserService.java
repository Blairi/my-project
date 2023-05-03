package com.blairi.myproject.myproject.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blairi.myproject.exception.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		
		Optional<User> userFound = userRepository.findById(id);
		
		if(userFound.isEmpty()) throw new ResourceNotFoundException("user", "id", id);
		
		return userFound.get();
	}
	
	public User save(User user) {
		// TODO: Hash the password
		return userRepository.save(user);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}
	
}
