package com.cancogang69.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.service.AccountService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/user")
public class UserController {
  
  @Autowired
  private AccountService userService;

  @GetMapping("/find/all")
  public List<Account> getAllUser() {
    return this.userService.findAllUser();
  }

  @GetMapping(path = "/find/id")
  public Optional<Account> getUserById(@RequestParam Integer id) {
    return this.userService.findUserById(id);
  }
  
  @PostMapping(path = "/create")
  public boolean createUser(@RequestBody Account newUser) {
    if(this.userService.findUserByEmail(newUser.getEmail()).isPresent()) {
      System.out.println("This email is taken by someone!");
      return false;
    }

    this.userService.saveUser(newUser);

    return true;
  } 

  @GetMapping(path = "/login")
  public String login(@RequestParam String email, @RequestParam String password) {
    if(this.userService.findUserByEmail(email).isEmpty()) {
      return "Email doesn't exist!";
    }

    Account someone = this.userService.findUserByEmailAndPassword(email, password);
    if(someone == null) {
      return "Password is incorrect!";
    }

    return "Login successful";
  }

  @PutMapping(path = "/update/email/{user_id}")
  public String updateEmail(@PathVariable Integer user_id, @RequestParam String email) {
    Optional<Account> update_user = this.userService.updateEmail(user_id, email);
    if(update_user.isPresent()) {
      return "Update email successful!";
    }
    else {
      return "Email has already been taken!";
    }
  }

  @PutMapping(path = "/update/password/{user_id}")
  public String updatePassword(@PathVariable Integer user_id, @RequestParam String password) {
    Optional<Account> update_user = this.userService.updatePassword(user_id, password);
    if(update_user.isPresent()) {
      return "Update email successful!";
    }
    else {
      return "Cannot find user!";
    }
  }

  @PutMapping(path = "/update/information/{user_id}")
  public String updateInformation(@PathVariable Integer user_id, @RequestBody Account update_user) {
    Optional<Account> respone_user = this.userService.updateInformation(user_id, update_user);

    if(respone_user.isPresent()) {
      return "Update user's information successful!";
    }
    else {
      return "Cannot update user's information!";
    }
  }

  @DeleteMapping(path = "/delete/{user_id}")
  public String deleteUser(@PathVariable Integer user_id, @RequestBody Account user) {
    boolean isDeleteUserSuccessful = this.userService.deleteUser(user_id, user);

    if(isDeleteUserSuccessful) {
      return "Delete account successful!";
    }
    else {
      return "Cannot delete account!";
    }
  }
}
