package com.cancogang69.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.enums.ChangeCode;
import com.cancogang69.todo.form.AccountForm;
import com.cancogang69.todo.repository.AccountRepository;

@Service
public class AccountService {
  
  @Autowired
  private AccountRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private PlanService planService;

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

  public ChangeCode updateEmail(String email, AccountForm form) {
    Optional<Account> someone = this.findByEmail(form.getNewEmail());
    if(someone.isPresent()) {
      return ChangeCode.EMAIL_BEEN_USED;
    } 

    Optional<Account> existing_user = 
      this.findByEmailAndPassword(email, form.getOldPassword());
    if(existing_user.isEmpty()) {
      return ChangeCode.WRONG_PASSWORD;
    }

    Account update_user = existing_user.get();
    update_user.setEmail(form.getNewEmail());
    userRepo.save(update_user);
    return ChangeCode.SUCCESSFUL;
  }

  public ChangeCode updatePassword(String email, AccountForm form) {
    Optional<Account> existing_user = this.findByEmailAndPassword(email, form.getOldPassword());
    if(existing_user.isEmpty()) {
      return ChangeCode.WRONG_PASSWORD;
    }

    Account update_user = existing_user.get();
    String hashedPassword = passwordEncoder.encode(form.getNewPassword());
    update_user.setPassword(hashedPassword);
    userRepo.save(update_user);
    return ChangeCode.SUCCESSFUL;
  }

  public ChangeCode updateInformation(String email, AccountForm form) {
    Optional<Account> existing_user = this.findByEmailAndPassword(email, form.getOldPassword());
    if(existing_user.isEmpty()) {
      return ChangeCode.WRONG_PASSWORD;
    }

    Account temp_user = existing_user.get();
    temp_user.setName(form.getNewUsername());
    userRepo.save(temp_user);
    return ChangeCode.SUCCESSFUL;
  }

  public ChangeCode deleteAccount(String email, String password) {
    Optional<Account> existing_user = this.findByEmailAndPassword(email, password);
    if(existing_user.isEmpty()) {
      return ChangeCode.WRONG_PASSWORD;
    }

    planService.deleteAllAccountPlan(existing_user.get().getId());
    return ChangeCode.SUCCESSFUL;
  }
}
