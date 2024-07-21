package com.cancogang69.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cancogang69.todo.service.TaskService;
import com.cancogang69.todo.entity.Task;
import com.cancogang69.todo.enums.TaskStatus;


@Controller
@RequestMapping(path = "/task")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping(path = "/{id}/completed")
  @PreAuthorize("isAuthenticated()")
  public String completeTask(@PathVariable Integer id) {
    boolean isUpdateStatusSuccessful = taskService.updateTaskStatus(id, TaskStatus.COMPLETED);
    if(isUpdateStatusSuccessful) {
      Optional<Task> task = taskService.getById(id);

      return "redirect:/plan/" + task.get().getPlanId();
    }
    else {
      return "404";
    }
  }

  @GetMapping(path = "/{id}/delete")
  @PreAuthorize("isAuthenticated()")
  public String deleteTask(@PathVariable Integer id) {
    Optional<Task> task = taskService.getById(id);
    Integer plan_id = task.get().getPlanId();
    boolean isUpdateStatusSuccessful = taskService.deleteTask(id);
    if(isUpdateStatusSuccessful) {
      return "redirect:/plan/" + plan_id;
    }
    else {
      return "404";
    }
  }
}
