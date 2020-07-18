package com.kulguy.findcomputer.payload;

import java.time.Instant;
import java.util.Objects;

public class UserProfile {
  private Long id;
  private String name;
  private String username;
  private String email;
  private String telp;
  private String description;
  private Instant joinedAt;


  public UserProfile() {
  }

  public UserProfile(Long id, String name, String username, String email, String telp, String description, Instant joinedAt) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.telp = telp;
    this.description = description;
    this.joinedAt = joinedAt;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public Instant getJoinedAt() {
    return this.joinedAt;
  }

  public void setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
  }

  public UserProfile id(Long id) {
    this.id = id;
    return this;
  }

  public UserProfile name(String name) {
    this.name = name;
    return this;
  }

  public UserProfile username(String username) {
    this.username = username;
    return this;
  }

  public UserProfile email(String email) {
    this.email = email;
    return this;
  }

  public UserProfile telp(String telp) {
    this.telp = telp;
    return this;
  }

  public UserProfile description(String description) {
    this.description = description;
    return this;
  }

  public UserProfile joinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserProfile)) {
            return false;
        }
        UserProfile userProfile = (UserProfile) o;
        return Objects.equals(id, userProfile.id) && Objects.equals(name, userProfile.name) && Objects.equals(username, userProfile.username) && Objects.equals(email, userProfile.email) && Objects.equals(telp, userProfile.telp) && Objects.equals(description, userProfile.description) && Objects.equals(joinedAt, userProfile.joinedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, username, email, telp, description, joinedAt);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", username='" + getUsername() + "'" +
      ", email='" + getEmail() + "'" +
      ", telp='" + getTelp() + "'" +
      ", description='" + getDescription() + "'" +
      ", joinedAt='" + getJoinedAt() + "'" +
      "}";
  }
  


}