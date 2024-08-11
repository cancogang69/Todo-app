package com.cancogang69.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.entity.Task;
import com.cancogang69.todo.service.AccountService;
import com.cancogang69.todo.service.PlanService;
import com.cancogang69.todo.service.TaskService;

import jakarta.validation.Valid;


@Controller
@RequestMapping(path = "/plan")
public class PlanController {
  
  @Autowired
  private AccountService accountService;

  @Autowired
  private PlanService planService;

  @Autowired
  private TaskService taskService;

  private String getLoggedInEmail() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    }
    return principal.toString();
  }

  @GetMapping(path = "/create")
  @PreAuthorize("isAuthenticated()")
  public String createPlan(Model model) {
    Plan newPlan = new Plan();
    model.addAttribute("plan", newPlan);
    return "plan_create";
  }
  
  @PostMapping(path = "/create")
  @PreAuthorize("isAuthenticated()")
  public String createPlan(@Valid Plan plan, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) {
      return "plan_create";
    }

    String email = getLoggedInEmail();
    Optional<Account> account = accountService.findByEmail(email);
    if(account.isEmpty())
      return "login";

    plan.setOwner(account.get());
    boolean isCreateSuccessful = planService.createPlan(plan);

    if(isCreateSuccessful) {
      return "redirect:/home";
    }
    else {
      return "error_page";
    }
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("isAuthenticated()")
  public String getPlanById(@PathVariable Integer id, Model model) {
    Optional<Plan> plan = planService.findById(id);
    if(plan.isPresent()) {
      List<Task> tasks = taskService.getByPlanId(id);
      model.addAttribute("plan", plan.get());
      model.addAttribute("tasks", tasks);
      return "plan";
    }
    else {
      return "404";
    }
  }

  @GetMapping(path = "/{planId}/create_task")
  @PreAuthorize("isAuthenticated()")
  public String addTask(@PathVariable Integer planId, Model model) {
    Optional<Plan> plan = planService.findById(planId);
    if(plan.isPresent()) {
      Task task = new Task();

      model.addAttribute("planId", plan.get().getId());
      model.addAttribute("task", task);
      return "task_create";
    }
    else {
      return "404";
    }
  }

  @PostMapping(path ="/{planId}/create_task")
  @PreAuthorize("isAuthenticated()")
  public String processAddTask(@PathVariable Integer planId, Model model, 
    @Valid Task task, BindingResult result) {

    if(result.hasErrors()) {
      return "task_create";
    }

    Optional<Plan> plan = planService.findById(planId);
    if(plan.isEmpty()) {
      return "404";
    }

    task.setPlan(plan.get());
    boolean isCreateSuccessful = taskService.createTask(task);
    if(isCreateSuccessful) {
      return "redirect:/plan/" + task.getPlanId().toString();
    }
    else {
      return "404";
    }
  }
}
