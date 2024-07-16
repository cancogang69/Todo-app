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
  public boolean postMethodName(@RequestBody User newUser) {
    if(this.userService.isEmailTaken(newUser.getEmail())) {
      System.out.println("This email is taken by someone!");
      return false;
    }

    this.userService.saveUser(newUser);

    return true;
  } 
}
