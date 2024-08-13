package com.cancogang69.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.validation.Valid;

import com.cancogang69.todo.service.TaskService;
import com.cancogang69.todo.entity.Task;
import com.cancogang69.todo.enums.TaskStatus;


@Controller
@RequestMapping(path = "/task")
public class TaskController {

  @Autowired
  private TaskService taskService;
  
  @PostMapping(path = "/{id}/status_change")
  @PreAuthorize("isAuthenticated()")
  public boolean changeTaskStatus(@PathVariable Integer id, @RequestBody String status) {
    TaskStatus newStatus = TaskStatus.fromJson(status);
    boolean isUpdateStatusSuccessful = taskService.updateTaskStatus(id, newStatus);
    return isUpdateStatusSuccessful;
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("isAuthenticated()")
  public String getTask(@PathVariable Integer id, Model model) {
    Optional<Task> task = taskService.getById(id);
    if(task.isEmpty()) {
      return "404";
    } 

    model.addAttribute("task", task.get());
    return "task_edit";
  }

  @PostMapping(path = "/{id}/edit")
  public String proccessUpdateTask(@PathVariable Integer id, @Valid Task editTask, BindingResult result) {
    if(result.hasErrors()) {
      return "task_edit";
    }

    boolean isUpdateSuccessful = taskService.updateTaskInformation(id, editTask);
    if(isUpdateSuccessful) {
      Optional<Task> updatetask = taskService.getById(id);
      return "redirect:/plan/" + updatetask.get().getPlanId();
    }
    else {
      return "404";
    }
  }

  @PostMapping(path = "/{id}/delete")
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
