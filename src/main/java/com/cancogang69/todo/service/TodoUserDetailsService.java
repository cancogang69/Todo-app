package com.cancogang69.todo.service;

import java.util.Optional;
import java.util.Collection;
import java.util.ArrayList;

import com.cancogang69.todo.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class TodoUserDetailsService implements UserDetailsService {
  
  @Autowired
  private AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Account> someone = accountService.findUserByEmail(email);
    if(someone.isEmpty()) {
      throw new UsernameNotFoundException("User doesn't exist!");
    }

    Account user = someone.get();
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("USER"));
    return new User(user.getEmail(), user.getPassword(), authorities);
  }
}
