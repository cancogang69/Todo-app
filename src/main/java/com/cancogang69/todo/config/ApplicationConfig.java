package com.cancogang69.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
  
  @Bean
  public static PasswordEncoder bcryptBPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
