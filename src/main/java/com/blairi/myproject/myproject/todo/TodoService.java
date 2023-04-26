package com.blairi.myproject.myproject.todo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blairi.myproject.exception.ResourceNotFoundException;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	public Todo save(Todo todo) {
		todo.setLastModified(LocalDate.now());
		return todoRepository.save(todo);
	}
	
	public Todo findById(Long id) {
		
		Optional<Todo> todoFound = todoRepository.findById(id);
		
		if(todoFound.isEmpty()) throw new ResourceNotFoundException("todo", "id", id);
		
		return todoFound.get();
	}
	
	public void deleteById(Long id) {
		Todo todoFound = this.findById(id);
		todoRepository.delete(todoFound);
	}
	
}
