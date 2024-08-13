package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Task;
import com.cancogang69.todo.enums.TaskStatus;
import com.cancogang69.todo.repository.TaskRepository;

@Service
public class TaskService {
  
  @Autowired
  private TaskRepository taskRepo;

  public List<Task> getAllTask() {
    return taskRepo.findAll();
  }

  public Optional<Task> getById(Integer id) {
    return taskRepo.findById(id);
  }

  public List<Task> getByPlanId(Integer plan_id) {
    return taskRepo.findAllTaskByPlanId(plan_id);
  }

  public boolean createTask(Task task) {
    Task saveTask = taskRepo.save(task);
    return (saveTask != null);
  }

  public boolean updateTaskStatus(Integer task_id, TaskStatus newStatus) {
    Optional<Task> task = getById(task_id);
    if(task.isEmpty()) {
      return false;
    }

    Task existing_task = task.get();
    existing_task.setStatus(newStatus);
    taskRepo.save(existing_task);
    return true;
  }

  public boolean updateTaskInformation(Integer taskId, Task updateTask) {
    Optional<Task> task = getById(taskId);
    if(task.isEmpty()) {
      return false;
    }

    Task existing_task = task.get();
    existing_task.setDescription(updateTask.getDescription());
    Task result = taskRepo.save(existing_task);
    return (result != null);
  }

  public boolean deleteTask(Integer task_id) {
    Optional<Task> task = getById(task_id);
    if(task.isEmpty()) {
      return false;
    }

    taskRepo.delete(task.get());
    return true;
  }
}
