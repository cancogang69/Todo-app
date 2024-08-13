package com.cancogang69.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cancogang69.todo.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
  
  @Query(value = "SELECT * FROM tasks WHERE plan_id = ?", nativeQuery = true)
  public List<Task> findAllTaskByPlanId(Integer plan_id);

  @Modifying
  @Query(value = "DELETE FROM tasks WHERE plan_id = ?", nativeQuery = true)
  public void deleteAllPlanTask(Integer plan_id);
}
