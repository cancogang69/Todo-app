package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Task;
import com.cancogang69.todo.enums.TaskStatus;
import com.cancogang69.todo.entity.Plan;
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

  public int createTask(Plan plan, Task task) {
    task.setPlan(plan);
    taskRepo.save(task);
    return 0;
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

  public boolean updateTaskInformation(Integer task_id, Task updateTask) {
    Optional<Task> task = getById(task_id);
    if(task.isEmpty()) {
      return false;
    }

    Task existing_task = task.get();
    existing_task.setStatus(updateTask.getStatus());
    existing_task.setDescription(updateTask.getDescription());
    taskRepo.save(existing_task);
    return true;
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
