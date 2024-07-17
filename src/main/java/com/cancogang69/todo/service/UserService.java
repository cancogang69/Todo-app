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

  public Optional<User> findUserByEmail(String email) {
    User someone = this.userRepo.findUserByEmail(email);
    return Optional.ofNullable(someone);
  }

  public User findUserByEmailAndPassword(String email, String password) {
    return this.userRepo.findUserByEmailAndPassword(email, password);
  }

  public User saveUser(User newUser) {
    return this.userRepo.save(newUser);
  }

  public Optional<User> updateEmail(Integer user_id, String update_email) {
    Optional<User> existing_user = this.findUserById(user_id);
    if(existing_user.isEmpty() || this.findUserByEmail(update_email).isPresent()) {
      return Optional.empty();
    } 

    User update_user = existing_user.get();
    update_user.setEmail(update_email);
    existing_user = Optional.of(this.userRepo.save(update_user));
    return existing_user;
  }

  public Optional<User> updatePassword(Integer user_id, String update_password) {
    Optional<User> existing_user = this.findUserById(user_id);
    if(existing_user.isEmpty()) {
      return Optional.empty();
    }

    User update_user = existing_user.get();
    update_user.setPassword(update_password);
    existing_user = Optional.of(this.userRepo.save(update_user));
    return existing_user;
  }

  private boolean isAuthCorrect(User existing_user, User stranger) {
    return (existing_user.getEmail().equals(stranger.getEmail()) &&
            existing_user.getPassword().equals(stranger.getPassword()));
  }

  public Optional<User> updateInformation(Integer user_id, User update_user) {
    Optional<User> existing_user = this.findUserById(user_id);
    if(existing_user.isEmpty() || !isAuthCorrect(existing_user.get(), update_user)) {
      return Optional.empty();
    }

    User temp_user = existing_user.get();
    temp_user.setName(update_user.getName());
    existing_user = Optional.of(this.userRepo.save(temp_user));
    return existing_user;
  }

  public boolean deleteUser(Integer user_id, User user) {
    Optional<User> existing_user = this.findUserById(user_id);
    if(existing_user.isEmpty() || !isAuthCorrect(existing_user.get(), user)) {
      return false;
    }

    this.userRepo.deleteById(user_id);
    return true;
  }
}
