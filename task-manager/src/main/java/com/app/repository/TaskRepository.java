package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	public Optional<Task> findByTaskTitle(String title);
	
	@Query("SELECT t FROM Task t JOIN t.profile p WHERE p.id=:id")
	public List<Task> findAllTaskByProfileId(Long id);
}
