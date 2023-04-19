package com.blairi.myproject.myproject.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blairi.myproject.myproject.user.User;
import com.blairi.myproject.myproject.user.UserService;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	
	public List<Project> findAllProjectsByOwnerId(Long id) {
		User user = userService.findById(id);
		return user.getProjects();
	}
	
}
