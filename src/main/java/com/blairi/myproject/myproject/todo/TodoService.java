package com.blairi.myproject.myproject.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	public Todo save(Todo todo) {
		return todoRepository.save(todo);
	}
	
}
