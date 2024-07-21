package com.cancogang69.todo.restcontroller;

import java.util.List;
import java.util.Optional;

import com.cancogang69.todo.service.PlanService;
import com.cancogang69.todo.service.TaskService;
import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.entity.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/task")
public class TaskRestController {
  
  @Autowired
  private TaskService taskService;

  @Autowired
  private PlanService planService;

  @GetMapping(path = "/find/all")
  public List<Task> getAllTask() {
    return taskService.getAllTask();
  }

  @GetMapping(path = "/find/{task_id}")
  public Task getById(@PathVariable Integer task_id) {
    Optional<Task> task = taskService.getById(task_id);
    if(task.isPresent()) {
      return task.get();
    }
    else {
      return null;
    }
  }

  @GetMapping(path = "/find/owner/{plan_id}")
  public List<Task> getAllTaskByPlanId(@PathVariable Integer plan_id) {
    return taskService.getByPlanId(plan_id);
  }
  
  @PostMapping(path = "/create")
  public String postMethodName(@RequestBody Task task, @RequestParam Integer plan_id) {
    Optional<Plan> plan = planService.findById(plan_id);

    if(plan.isEmpty()) {
      return "This plan doesn't exist!";
    }

    taskService.createTask(plan.get(), task);
    return "Create task successfully!";
  }
  
  @PutMapping(path = "/update/{task_id}")
  public String updateTaskInformation(@PathVariable Integer task_id, @RequestBody Task updateTask) {
    boolean isUpdateStatusSuccessful = taskService.updateTaskInformation(task_id, updateTask);
    
    if(isUpdateStatusSuccessful) {
      return "Update task successfully!";
    }
    else {
      return "This task doesn't exist!";
    }
  }

  @DeleteMapping(path = "/delete/{task_id}")
  public String deleteTask(@PathVariable Integer task_id) {
    boolean isDeleteTaskSuccessful = taskService.deleteTask(task_id);
    if(isDeleteTaskSuccessful) {
      return "Delete task successfully!";
    }
    else {
      return "This task doesn't exist!";
    }
  }
}
