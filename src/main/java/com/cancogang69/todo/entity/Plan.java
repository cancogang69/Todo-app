package com.cancogang69.todo.entity;

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
@Table(name = "plans")
public class Plan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotBlank(message = "Plan name cannot be blank.")
  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @ManyToOne
  @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
  private Account owner; 

  public Plan() { }

  public Plan(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Plan(String name, String description, Account owner) {
    this.name = name;
    this.description = description;
    this.owner = owner;
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOwner(Account owner) {
    this.owner = owner;
  }

  public Integer getOwnerId() {
    return this.owner.getId();
  }
}
