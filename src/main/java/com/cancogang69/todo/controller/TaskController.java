package com.cancogang69.todo.controller;

import java.util.List;

import com.cancogang69.todo.service.TaskService;
import com.cancogang69.todo.entity.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "api/task")
public class TaskController {
  
  @Autowired
  private TaskService taskService;

  @GetMapping(path = "/find/all")
  public List<Task> getAllTask() {
    return taskService.getAllTask();
  }
  
}
