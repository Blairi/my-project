package com.blairi.myproject.myproject.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.blairi.myproject.myproject.project.Project;
import com.blairi.myproject.myproject.project.ProjectRepository;
import com.blairi.myproject.myproject.todo.Todo;
import com.blairi.myproject.myproject.todo.TodoRepository;
import com.blairi.myproject.myproject.user.User;
import com.blairi.myproject.myproject.user.UserRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		// User
		User user1 = new User();
		user1.setName("Axel");
		
		// User's Project
		List<Project> projects1 = new ArrayList<>();
		
		Project project1 = new Project();
		project1.setProject("Build e-commerce");
		
		projects1.add(project1);
		
		// Project's todos
		List<Todo> todos1 = new ArrayList<>();
		
		Todo todo1 = new Todo();
		todo1.setDescription("create react app");
		todo1.setDone(false);
		Todo todo2 = new Todo();
		todo2.setDescription("create laravel back-end");
		todo2.setDone(true);
		
		todos1.add(todo1);
		todos1.add(todo2);
		
		// Setting
		todo1.setProject(project1);
		todo2.setProject(project1);
		user1.setProjects(projects1);
		project1.setUser(user1);
		project1.setTodos(todos1);
		
		// Saving
		userRepository.save(user1);
		projectRepository.save(project1);
		todoRepository.save(todo1);
		todoRepository.save(todo2);
		
		// User
		User user2 = new User();
		user2.setName("Andrea");
		
		// User's Project
		List<Project> projects2 = new ArrayList<>();
		
		Project project2 = new Project();
		project2.setProject("AI developing");
		
		projects2.add(project2);
		
		// Project's todos
		List<Todo> todos2 = new ArrayList<>();
		
		Todo todo3 = new Todo();
		todo3.setDescription("Use transformers");
		todo3.setDone(false);
		
		todos2.add(todo3);
		
		// Setting
		todo3.setProject(project2);
		user2.setProjects(projects2);
		project2.setUser(user2);
		project2.setTodos(todos2);
		
		// Saving
		userRepository.save(user2);
		projectRepository.save(project2);
		todoRepository.save(todo3);		
	}

}
