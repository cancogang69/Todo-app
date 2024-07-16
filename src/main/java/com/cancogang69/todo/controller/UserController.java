package com.cancogang69.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cancogang69.todo.entity.User;
import com.cancogang69.todo.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
  private UserService userService;

  @GetMapping("/find/all")
  public List<User> getAllUser() {
    return this.userService.findAllUser();
  }

  @GetMapping(path = "/find/id")
  public Optional<User> getUserById(@RequestParam Integer id) {
    return this.userService.findUserById(id);
  }
  
  @PostMapping(path = "/create")
  public boolean createUser(@RequestBody User newUser) {
    if(this.userService.isEmailTaken(newUser.getEmail())) {
      System.out.println("This email is taken by someone!");
      return false;
    }

    this.userService.saveUser(newUser);

    return true;
  } 

  @GetMapping(path = "/login")
  public String login(@RequestParam String email, @RequestParam String password) {
    if(!this.userService.isEmailTaken(email)) {
      return "Email doesn't exist!";
    }

    User someone = this.userService.findUserByEmailAndPassword(email, password);
    if(someone == null) {
      return "Password is incorrect!";
    }

    return "Login successful";
  }

  @PutMapping(path = "/update/email/{user_id}")
  public String updateEmail(@PathVariable Integer user_id, @RequestParam String email) {
    Optional<User> update_user = this.userService.updateEmail(user_id, email);
    if(update_user.isPresent()) {
      return "Update email successful!";
    }
    else {
      return "Email has already been taken!";
    }
  }

  @PutMapping(path = "/update/password/{user_id}")
  public String updatePassword(@PathVariable Integer user_id, @RequestParam String password) {
    Optional<User> update_user = this.userService.updatePassword(user_id, password);
    if(update_user.isPresent()) {
      return "Update email successful!";
    }
    else {
      return "Cannot find user!";
    }
  }
}
