package com.cancogang69.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.enums.ChangeCode;
import com.cancogang69.todo.form.AccountForm;
import com.cancogang69.todo.form.RegisterForm;
import com.cancogang69.todo.service.AccountService;

import jakarta.validation.Valid;

import java.util.Optional;


@Controller
public class AccountController {

  @Autowired
  private AccountService accountService;

  private String getLoggedInEmail() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDetails userDetails = (UserDetails) principal;
    return userDetails.getUsername();
  }

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

  @GetMapping(path = "/account")
  @PreAuthorize("isAuthenticated()")
  public String getAccountPage(Model model) {
    String email = getLoggedInEmail();
    Optional<Account> account = accountService.findByEmail(email);
    if(account.isEmpty()) {
      return "404";
    }

    model.addAttribute("account", account);
    model.addAttribute("accountForm", new AccountForm());
    return "account";
  }

  @PostMapping(path = "/account/change_information")
  @PreAuthorize("isAuthenticated()")
  public String changeAccountInformation(AccountForm accountForm) {
    ChangeCode status = accountService.updateInformation(getLoggedInEmail(), accountForm);
    
    switch (status) {
      case ChangeCode.WRONG_PASSWORD:
        return "redirect:/account?information_form_wrong_password";
      default:
        return "redirect:/account?information_change_successfully";
    }
  }

  @PostMapping(path = "/account/change_email")
  @PreAuthorize("isAuthenticated()")
  public String changeEmail(AccountForm accountForm) {
    ChangeCode status = accountService.updateEmail(getLoggedInEmail(), 
      accountForm.getOldPassword(), accountForm.getNewEmail());
    
    switch (status) {
      case ChangeCode.EMAIL_BEEN_USED:
        return "redirect:/account?email_has_been_taken";
      case ChangeCode.WRONG_PASSWORD:
        return "redirect:/account?email_form_wrong_password";
      default:
        return "redirect:/account?email_change_successfully";
    }
  }
}
