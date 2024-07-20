package com.cancogang69.todo.controller;

import com.cancogang69.todo.service.AccountService;
import com.cancogang69.todo.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {
  
  @Autowired
  private AccountService accountService;

  @GetMapping(path = "/register")
  public String getRegisterPage(Model model) {
    Account account = new Account();
    model.addAttribute("account", account);
    return "register";  
  }
  
  @PostMapping(path = "/register")
  public String registerNewAccount(@ModelAttribute Account account) {
    accountService.saveUser(account);
    return "redirect:/login";
  }
}
