package com.app.service;

import java.util.List;
import java.util.Map;

import com.app.model.Task;

public interface TaskService {

	public Task createTask(Task task, Long userId);
	public Map<String, List<Task>> getAllTasks();
	public Map<String, Task> getTaskById(Long id);
	public Task getTaskByTitle(String title);
	public Map<String, List<Task>> getAllTaskByProfileId(Long id);
	public Task updateTask(Task task);
	public Map<String, String> markTaskComplete(Long id);
	public Map<String, String> deleteTask(Long id);
}
