package com.blairi.myproject.myproject.project;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blairi.myproject.myproject.todo.Todo;
import com.blairi.myproject.myproject.todo.TodoService;
import com.blairi.myproject.myproject.user.User;
import com.blairi.myproject.myproject.user.UserService;

@RestController
public class ProjectResource {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TodoService todoService;
	
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
	
	@PostMapping("/projects/{id}/todos")
	public ResponseEntity<Object> createTodo(
			@PathVariable Long id, @RequestBody Todo todo) {
		
		Project projectFound = projectService.findById(id);
		
		todo.setProject(projectFound);
		todo.setDone(false);
		todo.setLastModified(LocalDate.now());
		
		Todo savedTodo = todoService.save(todo);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedTodo.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
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
	
	@PutMapping("/projects/{projectId}/todos/{id}")
	public ResponseEntity<Object> updateTodoById(
			@PathVariable Long projectId, @PathVariable Long id, 
			@RequestBody Todo todoUpdated) {
		
		if( !projectService.todoBelongsTo(id, projectId) )
			throw new RuntimeException("Todo not belongs to the project");
		
		Todo todoFound = todoService.findById(id);
		
		todoFound.setDescription(todoUpdated.getDescription());
		todoFound.setTargetDate(LocalDate.now());
		todoFound.setDone(todoUpdated.isDone());
		todoFound.setLastModified(LocalDate.now());
		
		todoService.save(todoFound);
		
		return ResponseEntity.ok().build();
	}
	
}
