package com.blairi.myproject.myproject.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blairi.myproject.myproject.todo.Todo;
import com.blairi.myproject.myproject.todo.TodoService;
import com.blairi.myproject.myproject.user.User;
import com.blairi.myproject.myproject.user.UserService;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TodoService todoService;
	
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
	
	public boolean todoBelongsTo(Long todoId, Long projectId) {
		
		Project projectFound = this.findById(projectId);
		
		Optional<Todo> todoFound = projectFound
				.getTodos().stream()
				.filter(todo -> todo.getId().equals(todoId)).findFirst();
		
		return todoFound.isPresent();
		
	}
	
	public void delete(Long id) {
		Project projectFound = this.findById(id);
		projectRepository.delete(projectFound);
	}
	
}
