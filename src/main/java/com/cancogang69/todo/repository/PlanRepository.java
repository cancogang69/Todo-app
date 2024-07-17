package com.cancogang69.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cancogang69.todo.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
    
}
