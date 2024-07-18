package com.cancogang69.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WelcomController {
  
  @GetMapping(path = {"/", "/welcome"})
  public String welcome(Model model) {
    return "welcome";
  } 
}
