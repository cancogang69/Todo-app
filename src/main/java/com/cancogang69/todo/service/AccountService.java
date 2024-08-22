package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.enums.ChangeCode;
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
    Account someone = userRepo.findUserByEmail(email);
    return Optional.ofNullable(someone);
  }

  public Optional<Account> findByEmailAndPassword(String email, String rawPassword) {
    Optional<Account> someone = this.findByEmail(email);
    String hashedPassword = someone.get().getPassword();
    System.out.println(hashedPassword);
    boolean isMatched = passwordEncoder.matches(rawPassword, hashedPassword);
    if(isMatched) {
      return someone;
    }
    else {
      return Optional.ofNullable(null);
    }
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

  public ChangeCode updateEmail(String email, String password, String update_email) {
    Optional<Account> someone = this.findByEmail(update_email);
    if(someone.isPresent()) {
      return ChangeCode.EMAIL_BEEN_USED;
    } 

    Optional<Account> existing_user = this.findByEmailAndPassword(email, password);
    if(existing_user.isEmpty()) {
      return ChangeCode.WRONG_PASSWORD;
    }

    Account update_user = existing_user.get();
    update_user.setEmail(update_email);
    existing_user = Optional.of(userRepo.save(update_user));
    return ChangeCode.SUCCESSFUL;
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
