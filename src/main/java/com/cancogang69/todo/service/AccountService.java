package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.repository.AccountRepository;

@Service
public class AccountService {
  
  @Autowired
  private AccountRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<Account> findAll() {
    return this.userRepo.findAll();
  }

  public Optional<Account> findById(Integer id) {
    return this.userRepo.findById(id);
  }

  public Optional<Account> findByEmail(String email) {
    Account someone = this.userRepo.findUserByEmail(email);
    return Optional.ofNullable(someone);
  }

  public Account findByEmailAndPassword(String email, String password) {
    return this.userRepo.findUserByEmailAndPassword(email, password);
  }

  public boolean saveUser(Account newAccount) {
    Optional<Account> someone = this.findByEmail(newAccount.getEmail());
    if(someone.isPresent()) {
      return false;
    }

    newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
    userRepo.save(newAccount);
    return true;
  }

  public Optional<Account> updateEmail(Integer user_id, String update_email) {
    Optional<Account> existing_user = this.findById(user_id);
    if(existing_user.isEmpty() || this.findByEmail(update_email).isPresent()) {
      return Optional.empty();
    } 

    Account update_user = existing_user.get();
    update_user.setEmail(update_email);
    existing_user = Optional.of(this.userRepo.save(update_user));
    return existing_user;
  }

  public Optional<Account> updatePassword(Integer user_id, String update_password) {
    Optional<Account> existing_user = this.findById(user_id);
    if(existing_user.isEmpty()) {
      return Optional.empty();
    }

    Account update_user = existing_user.get();
    update_user.setPassword(update_password);
    existing_user = Optional.of(this.userRepo.save(update_user));
    return existing_user;
  }

  private boolean isAuthCorrect(Account existing_user, Account stranger) {
    return (existing_user.getEmail().equals(stranger.getEmail()) &&
            existing_user.getPassword().equals(stranger.getPassword()));
  }

  public Optional<Account> updateInformation(Integer user_id, Account update_user) {
    Optional<Account> existing_user = this.findById(user_id);
    if(existing_user.isEmpty() || !isAuthCorrect(existing_user.get(), update_user)) {
      return Optional.empty();
    }

    Account temp_user = existing_user.get();
    temp_user.setName(update_user.getName());
    existing_user = Optional.of(this.userRepo.save(temp_user));
    return existing_user;
  }

  public boolean deleteUser(Integer user_id, Account user) {
    Optional<Account> existing_user = this.findById(user_id);
    if(existing_user.isEmpty() || !isAuthCorrect(existing_user.get(), user)) {
      return false;
    }

    this.userRepo.deleteById(user_id);
    return true;
  }
}
