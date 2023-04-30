package com.blairi.myproject.myproject.user;

import java.util.List;

import com.blairi.myproject.myproject.project.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min = 3)
	private String name;
	
	@Email
	private String email;
	
	@Size(min = 6)
	private String password;

	@OneToMany(mappedBy = "user")
	private List<Project> projects;

	public User() {
		super();
	}

	public User(Long id, String name, String email, String password, List<Project> projects) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.projects = projects;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

}
