package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.repository.PlanRepository;

@Service
public class PlanService {
  
  @Autowired
  private PlanRepository planRepo;

  @Autowired
  private TaskService taskService;

  public List<Plan> findAllPlan() {
    return this.planRepo.findAll();
  }

  public Optional<Plan> findById(Integer id) {
    return this.planRepo.findById(id);
  }

  public List<Plan> findByOwnerId(Integer id) {
    return this.planRepo.findByOwnerId(id);
  }

  public boolean createPlan(Plan newPlan) {
    newPlan = this.planRepo.save(newPlan);
    return (newPlan != null);
  }

  public boolean updatePlanInformation(Integer planId, Plan editPlan) {
    Optional<Plan> plan = findById(planId);
    if(plan.isEmpty()) {
      return false;
    }

    Plan existing_plan = plan.get();
    existing_plan.setName(editPlan.getName());
    existing_plan.setDescription(editPlan.getDescription());
    Plan updatePlan = planRepo.save(existing_plan);
    return (updatePlan != null);
  }

  public boolean deletePlan(Integer planId) {
    Optional<Plan> plan = this.findById(planId);
    if(plan.isEmpty()) {
      return false;
    }

    Plan existing_plan = plan.get();
    boolean isDeleteSuccessful = taskService.deleteAllPlanTask(planId);
    if(!isDeleteSuccessful) {
      return false;
    }
    
    planRepo.delete(existing_plan);
      return true;
  }

  public boolean deleteAllAccountPlan(Integer ownerId) {
    List<Plan> plans = this.findByOwnerId(ownerId);
    for(int i=0; i<plans.size(); i++)
      this.deletePlan(plans.get(i).getId());
    
    return true;
  }
}
