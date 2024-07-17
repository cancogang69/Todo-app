package com.cancogang69.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cancogang69.todo.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
  
  @Query(value = "SELECT * FROM plans WHERE owner_id = ?", nativeQuery = true)
  public List<Plan> findByOwnerId(Integer id);
}
