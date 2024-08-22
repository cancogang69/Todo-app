package com.cancogang69.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.form.RegisterForm;
import com.cancogang69.todo.service.AccountService;

import jakarta.validation.Valid;

@Controller
public class AccountController {

  @Autowired
  private AccountService accountService;

  @GetMapping(path = "/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("registerForm", new RegisterForm());
    return "register";  
  }
  
  @PostMapping(path = "/register")
  public String registerNewAccount(@Valid RegisterForm registerForm, 
    BindingResult result, Model model) {
    
    if(result.hasErrors()) {
      return "register";
    }

    Account newAccount = registerForm.createAccount();
    boolean isSaveSuccessful = accountService.saveUser(newAccount);
    if(isSaveSuccessful) {
      return "redirect:/login?create_successfully";
    }
    
    return "redirect:/register?already_exist";
  }

  @GetMapping(path = "/login") 
  public String getLoginPage() {
    return "login";
  }
}
