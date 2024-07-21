package com.cancogang69.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.entity.Plan;
import com.cancogang69.todo.service.AccountService;
import com.cancogang69.todo.service.PlanService;

@Controller
public class HomeController {
  
  @Autowired
  private AccountService accountService;

  @Autowired
  private PlanService planService;

  private String getLoggedInUsername() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    }
    return principal.toString();
  }

  @GetMapping(path = "/home")
  @PreAuthorize("isAuthenticated()")
  public String getHomePage(Model model) {
    String username = getLoggedInUsername();
    Optional<Account> account = accountService.findUserByEmail(username);

    List<Plan> plans = planService.findByOwnerId(account.get().getId());
    model.addAttribute("plans", plans);
    return "home";
  }
}
