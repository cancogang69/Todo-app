package com.cancogang69.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.entity.User;
import com.cancogang69.todo.service.PlanService;
import com.cancogang69.todo.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/plan")
public class PlanController {

  @Autowired
  private UserService userService;

  @Autowired
  private PlanService planService;
  
  @GetMapping(path = "/find/all")
  public List<Plan> getAllPlans() {
    return this.planService.findAllPlan();
  }

  @GetMapping(path = "/find/{plan_id}")
  public Optional<Plan> getPlanById(@PathVariable Integer plan_id) {
    return this.planService.findById(plan_id);
  }

  @GetMapping(path = "/find/owner/{email}")
  public List<Plan> getPlanByOwnerId(@PathVariable String email) {
    Optional<User> owner = this.userService.findUserByEmail(email);
    if(owner.isEmpty()) {
      return List.of();
    }

    Integer owner_id = owner.get().getId();
    return this.planService.findByOwnerId(owner_id);
  }
  

  @PostMapping(path = "/create")
  public String createPlan(@RequestBody Plan newPlan, @RequestParam String email) {
    Optional<User> owner = this.userService.findUserByEmail(email);
    if(owner.isEmpty()) {
      return "This user isn't exist!";
    }

    newPlan.setOwner(owner.get());
    boolean isCreateSuccessful = this.planService.createPlan(newPlan);

    if(isCreateSuccessful) {
      return "Create plan successfully!";
    }
    else {
      return "Cannot create plan!";
    }
  }
}
