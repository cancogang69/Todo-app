package com.cancogang69.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cancogang69.todo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  
}
