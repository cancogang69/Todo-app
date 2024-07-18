package com.cancogang69.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Task;
import com.cancogang69.todo.repository.TaskRepository;

@Service
public class TaskService {
  
  @Autowired
  private TaskRepository taskRepo;

  public List<Task> getAllTask() {
    return taskRepo.findAll();
  }
}
