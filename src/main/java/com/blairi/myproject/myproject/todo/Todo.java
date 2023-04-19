package com.blairi.myproject.myproject.todo;

import java.time.LocalDate;

import com.blairi.myproject.myproject.project.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Todo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private boolean done;
	private String description;
	private LocalDate targetDate;
	private LocalDate lastModified;

	@ManyToOne
	@JsonIgnore
	private Project project;

	public Todo() {
		super();
	}

	public Todo(Long id, boolean done, String description, LocalDate targetDate, LocalDate lastModified,
			Project project) {
		super();
		this.id = id;
		this.done = done;
		this.description = description;
		this.targetDate = targetDate;
		this.lastModified = lastModified;
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public LocalDate getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDate lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", done=" + done + ", description=" + description + ", targetDate=" + targetDate
				+ ", lastModified=" + lastModified + "]";
	}

}
