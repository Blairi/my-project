package com.blairi.myproject.myproject.project;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blairi.myproject.myproject.todo.Todo;
import com.blairi.myproject.myproject.user.User;
import com.blairi.myproject.myproject.user.UserService;

@RestController
public class ProjectResource {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/projects")
	public List<Project> retrieveAllProjects() {
		// TODO: Remove hard coded owner
		User user = userService.findById(1L);
		
		return user.getProjects();
	}
	
	@PostMapping("/projects")
	public ResponseEntity<Object> createProject(@RequestBody Project project) {
		
		// TODO: Remove hard coded owner
		User user = userService.findById(1L);
		project.setUser(user);
		
		Project savedProject = projectService.save(project);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProject.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/projects/{id}")
	public Project retrieveProjectById(@PathVariable Long id) {
		return projectService.findById(id);
	}
	
	@GetMapping("/projects/{id}/todos")
	public List<Todo> retrieveAllTodosByProjectId(@PathVariable Long id) {
		return projectService.findById(id).getTodos();
	}
	
	@GetMapping("/projects/{projectId}/todos/{id}")
	public Todo retrieveProjectTodoByTodoId(
			@PathVariable Long projectId, @PathVariable Long id) {
		
		List<Todo> todos = projectService.findById(projectId).getTodos();
		
		Optional<Todo> todoFound = todos.stream()
				.filter(todo -> todo.getId().equals(id))
				.findFirst();
		
		if(todoFound.isEmpty()) throw new RuntimeException("Todo not found"); 
		
		return todoFound.get();
	}
	
}
