package com.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Task;
import com.app.service.TaskService;

import jakarta.validation.Valid;

/*
  {
 "taskTitle":"Task One ",
 "taskDesc":"This is Task One",
 "status":"Pending"
 }
 
 {
 "taskTitle":"Task One ",
 "taskDesc":"This is Task One",
 "status":"Pending"
 }
 {
  "taskTitle": "Task Two",
  "taskDesc": "This is task Two",
  "status": "pending",
  "profileId": 3
}
 */

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping(value = "/createTask/{userId}")
	public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, @PathVariable("userId") Long userId) {
		System.out.println("Hii");
		return new ResponseEntity<Task>(taskService.createTask(task, userId),HttpStatus.ACCEPTED);
	}
	@GetMapping(value = "/getAllTasks")
	public ResponseEntity<Map<String, List<Task>>> getAllTasks(){
		return new ResponseEntity<Map<String, List<Task>>>(taskService.getAllTasks(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/getAllTasksByProfileId/{id}")
	public ResponseEntity<Map<String, List<Task>>> getAllTaskByProfileId(@PathVariable("id") Long id ){
		return new ResponseEntity<Map<String, List<Task>>>(taskService.getAllTaskByProfileId(id),HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/getTaskById/{id}")
	public ResponseEntity<Map<String, Task>> getTaskById(@PathVariable("id") Long id) {
		return new ResponseEntity<Map<String, Task>>(taskService.getTaskById(id),HttpStatus.ACCEPTED);
	}
	@GetMapping(value = "/getTaskByTitle/{title}")
	public ResponseEntity<Task> getTaskByTitle(@PathVariable("title")String title){
		return new ResponseEntity<Task>(taskService.getTaskByTitle(title),HttpStatus.ACCEPTED);
	}
	@PutMapping(value = "/updtateTask")
	public ResponseEntity<Task> updateTask(@RequestBody Task task){
		return new ResponseEntity<Task>(taskService.updateTask(task),HttpStatus.ACCEPTED);
	}
	@DeleteMapping(value = "/deleteTaskById/{id}")
	public ResponseEntity<Map<String, String>> deleteTaskById(@PathVariable("id") Long id){
		
		return new ResponseEntity<Map<String, String>>(taskService.deleteTask(id),HttpStatus.ACCEPTED);
	}
	@GetMapping(value = "/markTaskComplete/{id}")
	public ResponseEntity<Map<String, String>> markTaskComplete(@PathVariable("id") Long id){
		return new ResponseEntity<Map<String, String>>(taskService.markTaskComplete(id),HttpStatus.ACCEPTED);
	}
}
