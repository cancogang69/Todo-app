package com.cancogang69.todo.controller;

import com.cancogang69.todo.service.AccountService;

import jakarta.validation.Valid;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.form.RegisterForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {
  
  @Autowired
  private AccountService accountService;

  @GetMapping(path = "/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("accountForm", new RegisterForm());
    return "register";  
  }
  
  @PostMapping(path = "/register")
  public String registerNewAccount(@Valid RegisterForm registerForm, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) {
      return "register";
    }

    Account newAccount = registerForm.createAccount();
    boolean isSaveSuccessful = accountService.saveUser(newAccount);
    if(isSaveSuccessful) {
      return "redirect:/login?create_success";
    }
    
    return "register?already_exist";
  }
}
