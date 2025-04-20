package org.springmvc.java8.controller;


import java.util.NoSuchElementException;

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
				return this.getResponse(null,HttpStatus.NO_CONTENT);
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
		try{
			Task taskToUpdate = repository.findById(id).get();
			if(taskToUpdate == null){
				logger.error("Task not found with id: " + id);
				return this.getResponse(null,HttpStatus.NO_CONTENT);
			}

			taskToUpdate.setTitle(task.getTitle());
			taskToUpdate.setDescription(task.getDescription());
			if(task.getStatus() != null){
				taskToUpdate.setTaskStatus(TaskStatus.valueOf(task.getStatus()));
			}else {
				logger.warn("Task status is null, defaulting to CREATED");
			}			
			repository.save(taskToUpdate);
			logger.info("Task updated with id: " + taskToUpdate.getId());
			res.setBody(task.getId());
			res.setMessage("Task updated successfully");
		} catch(Exception e){
			logger.error("Error while updating task", e);
			res.setMessage("Unexpected error occured");
			res.setStatus(500);
		}
		return this.getResponse(res,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteTask(@PathVariable Long id){
		Response res = new Response();
		try{
			Task taskToDelete = repository.findById(id).get();
			repository.delete(taskToDelete);
			logger.info("Task deleted with id: " + taskToDelete.getId());
			res.setBody(taskToDelete.getId().toString());
			res.setMessage("Task deleted successfully");
		} catch (NoSuchElementException e){
			logger.error("Task not found with id: " + id);
//			res.setMessage("Task not found");
//			res.setSuccess(false);
//			res.setStatus(HttpStatus.NO_CONTENT.value());
//			return this.getResponse(res,HttpStatus.OK);
			
			//NO_CONTENT is not sending a body
			return this.getResponse(null,HttpStatus.NO_CONTENT);
		} catch(Exception e){
			logger.error("Error while deleting task", e);
			res.setMessage("Unexpected error occurred");
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return this.getResponse(res,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return this.getResponse(res,HttpStatus.OK);
	}


	private <T> ResponseEntity<T> getResponse(T t, HttpStatus status) {
		return new ResponseEntity<>(t, status);
	}
}