package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.User;
import com.cancogang69.todo.repository.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepo;

  public List<User> findAllUser() {
    return this.userRepo.findAll();
  }

  public Optional<User> findUserById(Integer id) {
    return this.userRepo.findById(id);
  }

  public boolean isEmailTaken(String email) {
    User someone = this.userRepo.findUserByEmail(email);
    return !(someone == null);
  }

  public User findUserByEmailAndPassword(String email, String password) {
    return this.userRepo.findUserByEmailAndPassword(email, password);
  }

  public User saveUser(User newUser) {
    return this.userRepo.save(newUser);
  }
}
