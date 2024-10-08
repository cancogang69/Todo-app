package com.cancogang69.todo.config;

import java.lang.Exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/**").permitAll();
        auth.requestMatchers("/register/**").permitAll();
        auth.anyRequest().authenticated();
      })
      .formLogin(form -> form
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/home")
        .failureUrl("/login?error")
        .permitAll()
      )
      .logout(logout -> logout
        .logoutSuccessUrl("/login?logout")
        .permitAll()
      );
    
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
