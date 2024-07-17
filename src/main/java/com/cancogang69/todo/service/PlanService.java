package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.entity.User;
import com.cancogang69.todo.repository.PlanRepository;

@Service
public class PlanService {
  
  @Autowired
  private PlanRepository planRepo;

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

  public int updatePlanInformation(Integer plan_id, User owner, Plan updatePlan) {
    Optional<Plan> plan = this.findById(plan_id);
    if(plan.isEmpty()) {
      return 1;
    }

    Plan existing_plan = plan.get();
    if(existing_plan.getOwnerId() != owner.getId()) {
      return 2;
    }

    existing_plan.setName(updatePlan.getName());
    existing_plan.setDescription(updatePlan.getDescription());
    Plan plan_after_update = this.planRepo.save(existing_plan);

    if(plan_after_update != null) {
      return 0;
    }
    else {
      return 3;
    }
  }
}
