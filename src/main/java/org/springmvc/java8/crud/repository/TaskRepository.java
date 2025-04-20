package org.springmvc.java8.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springmvc.java8.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	// This interface will automatically be implemented by Spring Data JPA
	// No need to write any code here
	// You can add custom query methods if needed
}
