package com.blairi.myproject.myproject.project;

import java.util.List;
import java.util.Optional;

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
	
	public Project save(Project project) {
		return projectRepository.save(project);
	}
	
	public List<Project> findAllProjectsByOwnerId(Long id) {
		User user = userService.findById(id);
		return user.getProjects();
	}
	
	public Project findById(Long id) {
		
		Optional<Project> projectFound = projectRepository.findById(id);
		
		if(projectFound.isEmpty()) throw new RuntimeException("Project not found");
		
		return projectFound.get();
	}
	
}
