package com.cancogang69.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  public String createPlan(@ModelAttribute Plan plan) {
    String email = getLoggedInEmail();
    Optional<Account> account = accountService.findUserByEmail(email);
    if(account.isEmpty())
      return "/home";

    plan.setOwner(account.get());
    boolean isCreateSuccessful = planService.createPlan(plan);
    if(isCreateSuccessful) {
      return "redirect:/home";
    }
    else {
      return "";
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
  
  @GetMapping(path = "/{id}/create_task")
  @PreAuthorize("isAuthenticated()")
  public String getMethodName(@PathVariable Integer id, Model model) {
    Optional<Plan> plan = planService.findById(id);
    if(plan.isPresent()) {
      model.addAttribute("plan", plan.get());

      Task task = new Task();
      model.addAttribute("task", task);
      return "task_create";
    }
    else {
      return "404";
    }
  }

  @PostMapping(path = "/{id}/create_task")
  @PreAuthorize("isAuthenticated()")
  public String postMethodName(@ModelAttribute Task task, @PathVariable Integer id) {
    Optional<Plan> plan = planService.findById(id);

    if(plan.isEmpty()) {
      return "404";
    }

    int isCreateSuccessful = taskService.createTask(plan.get(), task);
    if(isCreateSuccessful == 0) {
      return "redirect:/plan/" + id.toString();
    }
    else {
      return "404";
    }
  }
  
}
