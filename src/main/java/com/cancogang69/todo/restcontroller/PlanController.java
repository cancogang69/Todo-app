package com.cancogang69.todo.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.service.PlanService;
import com.cancogang69.todo.service.AccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/plan")
public class PlanController {

  @Autowired
  private AccountService userService;

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
    Optional<Account> owner = this.userService.findUserByEmail(email);
    if(owner.isEmpty()) {
      return List.of();
    }

    Integer owner_id = owner.get().getId();
    return this.planService.findByOwnerId(owner_id);
  }
  

  @PostMapping(path = "/create")
  public String createPlan(@RequestBody Plan newPlan, @RequestParam String email) {
    Optional<Account> owner = this.userService.findUserByEmail(email);
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

  @PutMapping("/update/{plan_id}")
  public String updatePlanInformation(@PathVariable Integer plan_id, @RequestParam String email, 
                  @RequestBody Plan updatePlan) {

    Optional<Account> owner = this.userService.findUserByEmail(email);
    if(owner.isEmpty()) {
      return "This user isn't exist!";
    }

    int status = this.planService.updatePlanInformation(plan_id, owner.get(), updatePlan);
    switch (status) {
      case 1:
        return "This plan doesn't exist";
      case 2:
        return "This user doesn't own this plan!";
      case 3:
        return "Some issue happen when saving update information!";
      default:
        return "Update plan succesful!";
    }
  }

  @DeleteMapping(path = "/delete/{plan_id}")
  public String deletePlan(@PathVariable Integer plan_id, @RequestParam String email) {
    Optional<Account> owner = this.userService.findUserByEmail(email);
    if(owner.isEmpty()) {
      return "This user isn't exist!";
    }

    int status = this.planService.deletePlan(plan_id, owner.get());
    switch (status) {
      case 1:
        return "This plan doesn't exist";
      case 2:
        return "This user doesn't own this plan!";
      default:
        return "Delete plan succesful!";
    }
  }
}
