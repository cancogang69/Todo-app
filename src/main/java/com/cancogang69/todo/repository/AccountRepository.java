package com.cancogang69.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cancogang69.todo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  @Query(value = "SELECT * FROM users WHERE email = ?", nativeQuery = true)
  public Account findUserByEmail(String email);

  @Query(value = "SELECT * FROM users WHERE email = ?1 AND password = ?2", nativeQuery = true)
  public Account findUserByEmailAndPassword(String email, String password);
}
