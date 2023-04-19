package com.blairi.myproject.myproject.project;

import java.util.List;

import com.blairi.myproject.myproject.todo.Todo;
import com.blairi.myproject.myproject.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private Long id;

	private String project;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "project")
	private List<Todo> todos;

	public Project() {
		super();
	}

	public Project(Long id, String project, User user, List<Todo> todos) {
		super();
		this.id = id;
		this.project = project;
		this.user = user;
		this.todos = todos;
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", project=" + project + "]";
	}

}
