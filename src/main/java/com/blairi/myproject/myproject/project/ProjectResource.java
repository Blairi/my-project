package com.blairi.myproject.myproject.project;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blairi.myproject.exception.ResourceNotFoundException;
import com.blairi.myproject.myproject.todo.Todo;
import com.blairi.myproject.myproject.todo.TodoService;
import com.blairi.myproject.myproject.user.User;
import com.blairi.myproject.myproject.user.UserService;

import jakarta.validation.Valid;

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
	public ResponseEntity<Object> createProject(@Valid @RequestBody Project project) {
		
		// TODO: Remove hard coded owner
		User user = userService.findById(1L);
		project.setUser(user);
		
		Project savedProject = projectService.save(project);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProject.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/projects/{id}")
	public ResponseEntity<Object> updateProjectById(
			@PathVariable Long id, @Valid @RequestBody Project projectUpdated) {
		
		Project projectFound = projectService.findById(id);
		projectFound.setProject( projectUpdated.getProject() );
		
		projectService.save(projectFound);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/projects/{id}")
	public Project retrieveProjectById(@PathVariable Long id) {
		return projectService.findById(id);
	}
	
	@DeleteMapping("/projects/{id}")
	public ResponseEntity<Object> deleteProjectById(@PathVariable Long id) {
		projectService.delete(id);
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping("/projects/{id}/todos")
	public List<Todo> retrieveAllTodosByProjectId(@PathVariable Long id) {
		return projectService.findById(id).getTodos();
	}
	
	@PostMapping("/projects/{id}/todos")
	public ResponseEntity<Object> createTodo(
			@PathVariable Long id, @Valid @RequestBody Todo todo) {
		
		Project projectFound = projectService.findById(id);
		
		todo.setProject(projectFound);
		todo.setDone(false);
		
		Todo savedTodo = todoService.save(todo);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedTodo.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping("/projects/{projectId}/todos/{id}")
	public Todo retrieveProjectTodoByTodoId(
			@PathVariable Long projectId, @PathVariable Long id) { 
		return projectService.findProjectTodo(projectId, id);
	}
	
	@PutMapping("/projects/{projectId}/todos/{id}")
	public ResponseEntity<Object> updateTodoById(
			@PathVariable Long projectId, @PathVariable Long id, 
			@Valid @RequestBody Todo todoUpdated) {
		
		Todo todoFound = projectService.findProjectTodo(projectId, id);
		
		todoFound.setDescription(todoUpdated.getDescription());
		todoFound.setTargetDate(LocalDate.now());
		todoFound.setDone(todoUpdated.isDone());
		
		todoService.save(todoFound);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/projects/{projectId}/todos/{id}")
	public ResponseEntity<Object> deleteTodoById(
			@PathVariable Long projectId, @PathVariable Long id) {
		
		if( !projectService.todoBelongsTo(id, projectId) )
			throw new RuntimeException("Todo not belongs to the project");
		
		todoService.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
