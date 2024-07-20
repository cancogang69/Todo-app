package com.cancogang69.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  
  @GetMapping(path = "/login") 
  public String getLoginPage() {
    return "login";
  }
}
