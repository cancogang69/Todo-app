package com.cancogang69.todo.entity;

import com.cancogang69.todo.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tasks")
public class Task {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotBlank(message = "Task must have description")
  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private TaskStatus status;

  @ManyToOne
  @JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false)
  private Plan plan;

  public Task() { 
    this.status = TaskStatus.INITIAL;
  }

  public Task(String description) {
    this.description = description;
    this.status = TaskStatus.INITIAL;
  }

  public Task(String description, Plan plan) {
    this.description = description;
    this.status = TaskStatus.INITIAL;
    this.plan = plan;
  }

  public Integer getId() {
    return this.id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TaskStatus getStatus() {
    return this.status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public Integer getPlanId() {
    return this.plan.getId();
  }
}
