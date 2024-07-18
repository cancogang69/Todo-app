package com.cancogang69.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cancogang69.todo.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
  
}
