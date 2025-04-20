package org.springmvc.java8.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springmvc.java8.crud.repository.TaskRepository;
import org.springmvc.java8.model.Task;

@RestController
@RequestMapping("/tasks")
@ResponseBody
public class TaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private TaskRepository repository;

	@PostMapping()
	public ResponseEntity createTask(@RequestBody TaskDto task){
		Response res = new Response();
		if(task == null){
			res.setMessage("Input cannot be null");
			res.setSuccess(false);
			return this.getResponse(res,HttpStatus.OK);
		}

		try{
			Task saveTask = new Task(task.getTitle());
			saveTask.setDescription(task.getDescription());
			
			if(task.getStatus() != null){
				saveTask.setTaskStatus(TaskStatus.valueOf(task.getStatus()));
			}else {
				logger.warn("Task status is null, defaulting to CREATED");
			}
			Task taskToSave = repository.save(saveTask);
			logger.info("Task saved with id: " + taskToSave.getId());
			
			res.setBody(taskToSave.getId().toString());
			res.setMessage("Task saved successfully");
		} catch(Exception e){
			//TODO Log exception
			logger.error("Error while saving task", e);
			res.setMessage("Unexpected error occured");
			res.setStatus(500);
			return this.getResponse(res,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return this.getResponse(res,HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity readTask(@PathVariable Long id){
		try{
			Task task = repository.findById(id).get();
			if(task == null){
				return this.getResponse(task,HttpStatus.NO_CONTENT);
			}else{
				TaskDto taskDto = new TaskDto();
				taskDto.setId(task.getId().toString());
				taskDto.setTitle(task.getTitle());
				taskDto.setDescription(task.getDescription());
				taskDto.setStatus(task.getStatus().name());
				return this.getResponse(taskDto,HttpStatus.OK);
			}
		} catch(Exception e){
			logger.error("Error while fetching task", e);
			Response res = new Response();
			res.setMessage("Unexpected error occurred");
			res.setStatus(500);
			return this.getResponse(res,HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/{id}")
	public ResponseEntity updateTask(@PathVariable Long id, @RequestBody TaskDto task){
		Response res = new Response();
		if(task == null){
			logger.error("Input task is null");
			res.setMessage("Input caanot be null");
			res.setSuccess(false);
			return this.getResponse(res,HttpStatus.OK);
		}

		try{
			Task taskToUpdate = repository.findById(id).get();
			if(taskToUpdate == null){
				logger.error("Task not found with id: " + id);
				res.setMessage("Task not found");
				res.setSuccess(false);
				return this.getResponse(res,HttpStatus.NO_CONTENT);
			}

			taskToUpdate.setTitle(task.getTitle());
			taskToUpdate.setDescription(task.getDescription());
			if(task.getStatus() != null){
				taskToUpdate.setTaskStatus(TaskStatus.valueOf(task.getStatus()));
			}else {
				logger.warn("Task status is null, defaulting to CREATED");
			}			
			repository.save(taskToUpdate);
			res.setBody(task.getId());
			res.setMessage("Task updated successfully");
		} catch(Exception e){
			//TODO Log exception
			res.setMessage("Unexpected error occured");
			res.setStatus(500);
		}
		return this.getResponse(res,HttpStatus.OK);
	}


	private <T> ResponseEntity<T> getResponse(T t, HttpStatus status) {
		return new ResponseEntity<>(t, status);
	}
}

//
//class TaskDto {
//	private String id;
//	private String title;
//	private String description;
//	private String status;
//
//	TaskDto(String id, String title, String description, String status) {
//		this.id = id;
//		this.title = title;
//		this.description = description;
//		this.status = status;
//	}
//
//	TaskDto() {}
//
//	public String getId() {
//		return id;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//}
//
//enum TaskStatus {
//	CREATED, APPROVED, REJECTED, BLOCKED, DONE
//}
//
//@Entity
//class Task {
//	@Id
//	@GeneratedValue
//	private Long id;
//
//	private String title;
//	private String description;
//	private TaskStatus status = CREATED;
//
//	public Task(String title) {
//		this.title = title;
//	}
//
//	private Task() {}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public void setTaskStatus(TaskStatus status) {
//		this.status = status;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public TaskDto toDto() {
//		return new TaskDto(String.valueOf(id), title, description, status.name());
//	}
//}
//
//
//class Response{
//	private int status;
//	private boolean success;
//	private String message;
//	private int body;
//
//	public Response() {
//		this.status = 200;
//		this.success = true;
//		this.message = "";
//		this.body = 0;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public boolean isSuccess() {
//		return success;
//	}
//
//	public void setSuccess(boolean success) {
//		this.success = success;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public int getBody() {
//		return body;
//	}
//
//	public void setBody(int body) {
//		this.body = body;
//	}
//
//}