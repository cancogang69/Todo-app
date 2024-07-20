package com.cancogang69.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Authority {
  
  @Id
  @Column(length = 20)
  private String name;

  @Override
  public boolean equals(Object object) {
    if(this == object)
      return true;

    if(object == null || getClass() != object.getClass())
      return false;

    Authority authority = (Authority) object;
    return name.equals(authority.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
