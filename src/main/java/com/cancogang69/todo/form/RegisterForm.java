package com.cancogang69.todo.form;

import com.cancogang69.todo.entity.Account;
import com.cancogang69.todo.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterForm {
  @NotBlank(message = "User must have name")
  private String name;

  @Email
  @NotBlank(message = "User must have email")
  private String email;

  @NotBlank(message = "Please provide a password")
  private String password;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }

  public Account createAccount() {
    return new Account(name, email, password, UserRole.USER);
  }
}
