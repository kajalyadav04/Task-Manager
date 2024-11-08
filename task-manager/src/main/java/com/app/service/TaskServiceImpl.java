package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.TaskNotFoundException;
import com.app.model.Profile;
import com.app.model.Task;
import com.app.repository.ProfileRepository;
import com.app.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	
	@Override
	public Task createTask(Task task, Long userId) {
		if(task==null) throw new TaskNotFoundException("Task Cannot be null");
		
		Profile profile = profileRepository
				            .findById(userId)
	                            .orElseThrow(()->new TaskNotFoundException("Task Cannot be null"));
		
		task.setProfile(profile);

		
		return taskRepository.save(task);
	}

	@Override
	public Map<String, List<Task>> getAllTasks() {
		List<Task> tasks = taskRepository.findAll();
		if(tasks.isEmpty())throw new TaskNotFoundException("Task Store Empty! Not any Task to Show...");
		Map<String, List<Task>> map = new HashMap<>();
		map.put("data", tasks);
		return map;
//		return tasks;
	}

	@Override
	public Map<String, Task> getTaskById(Long id) {
		Optional<Task> optional = taskRepository.findById(id);
		if(optional.isEmpty()) {
			new TaskNotFoundException("Task Not Found With id:- "+id);
		}
		Map<String, Task> map = new HashMap<>();
		map.put("task", optional.get());
		
		return map;
	}

	@Override
	public Task getTaskByTitle(String title) {
		 
		Task task = taskRepository.findByTaskTitle(title).orElseThrow(
				()-> new TaskNotFoundException("Task Not Found With id:- "+title)
				);
		return task;
	}

	@Override
	public Task updateTask(Task task) {

		Long id = task.getId();
		
		Task perTask = taskRepository.findById(id).orElseThrow(
				()-> new TaskNotFoundException("Task Not Found With id:- "+id)
				);
		if(task.getStatus()!=null)
		    perTask.setStatus(task.getStatus());
		
		if(task.getTaskTitle()!=null)
			perTask.setTaskTitle(task.getTaskTitle());
		
		if(task.getTaskDesc()!=null)
			perTask.setTaskDesc(task.getTaskDesc());
		
		return taskRepository.save(perTask);
	}

	@Override
	public Map<String, String> deleteTask(Long id){
		
//		Task task = taskRepository.findById(id).orElseThrow(
//				()-> new TaskNotFoundException("Task Not Found With id:- "+id)
//				);
		
		Optional<Task> optional = taskRepository.findById(id);
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "Task with id: "+id+" not Found");
			return map;		
		}
		else {
			Task task = optional.get();
		taskRepository.delete(task);
		Map<String, String> map = new HashMap<>();
		map.put("message", "Task: "+task.getTaskTitle()+" Deleted!");
		return map;
		}
		
		
	}

	@Override
	public Map<String, String> markTaskComplete(Long id) {

		Optional<Task> optional = taskRepository.findById(id);
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "Task with id: "+id+" not Found");
			return map;		
		}
		else {
		Task task = optional.get();
		task.setStatus("Completed");
		taskRepository.save(task);
		Map<String, String> map = new HashMap<>();
		map.put("message", "Task: "+task.getTaskTitle()+" Completed!");
		return map;
		}
	}

	@Override
	public Map<String, List<Task>> getAllTaskByProfileId(Long id) {
		List<Task> tasks = taskRepository.findAllTaskByProfileId(id);
		if(tasks.isEmpty())throw new TaskNotFoundException("Task Store Empty! Not any Task to Show...");
		Map<String, List<Task>> map = new HashMap<>();
		map.put("data", tasks);
		return map;
	}

}
