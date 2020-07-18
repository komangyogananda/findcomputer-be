package com.kulguy.findcomputer.payload;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequest {
  private Long id;
  
  @NotBlank
  private String username;

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String telp;

  @NotBlank
  private String description;
  

  public UserRequest() {
  }

  public UserRequest(Long id, String username, String name, String email, String telp, String description) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.email = email;
    this.telp = telp;
    this.description = description;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelp() {
    return this.telp;
  }

  public void setTelp(String telp) {
    this.telp = telp;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UserRequest id(Long id) {
    this.id = id;
    return this;
  }

  public UserRequest username(String username) {
    this.username = username;
    return this;
  }

  public UserRequest name(String name) {
    this.name = name;
    return this;
  }

  public UserRequest email(String email) {
    this.email = email;
    return this;
  }

  public UserRequest telp(String telp) {
    this.telp = telp;
    return this;
  }

  public UserRequest description(String description) {
    this.description = description;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserRequest)) {
            return false;
        }
        UserRequest userRequest = (UserRequest) o;
        return Objects.equals(id, userRequest.id) && Objects.equals(username, userRequest.username) && Objects.equals(name, userRequest.name) && Objects.equals(email, userRequest.email) && Objects.equals(telp, userRequest.telp) && Objects.equals(description, userRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, name, email, telp, description);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", username='" + getUsername() + "'" +
      ", name='" + getName() + "'" +
      ", email='" + getEmail() + "'" +
      ", telp='" + getTelp() + "'" +
      ", description='" + getDescription() + "'" +
      "}";
  }

}