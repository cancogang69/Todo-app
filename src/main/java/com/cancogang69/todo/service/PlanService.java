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

  public List<Plan> findAllPlan() {
    return this.planRepo.findAll();
  }

  public Optional<Plan> findById(Integer id) {
    return this.planRepo.findById(id);
  }

  public boolean createPlan(Plan newPlan) {
    newPlan = this.planRepo.save(newPlan);
    return (newPlan != null);
  }
}
