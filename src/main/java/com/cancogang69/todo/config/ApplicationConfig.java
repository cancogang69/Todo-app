package com.cancogang69.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cancogang69.todo.service.AccountService;

@Configuration
public class ApplicationConfig {

  @Autowired
  private AccountService accountService;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> accountService.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User didn't exist!"));
  }
}
