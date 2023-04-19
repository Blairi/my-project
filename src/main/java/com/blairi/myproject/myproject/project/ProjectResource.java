package com.blairi.myproject.myproject.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blairi.myproject.myproject.todo.Todo;

@RestController
public class ProjectResource {
	
	@Autowired
	private ProjectService projectService;
	
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
