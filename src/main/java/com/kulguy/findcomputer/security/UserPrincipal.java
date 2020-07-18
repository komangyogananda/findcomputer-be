package com.kulguy.findcomputer.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kulguy.findcomputer.model.RoleType;
import com.kulguy.findcomputer.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

  private Long id;

  private String username;

  private String email;

  private String telp;

  private String description;

  @JsonIgnore
  private String password;
  
  private Collection<? extends GrantedAuthority> authorities;


  public UserPrincipal(Long id, String username, String email, String telp, String description, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.telp = telp;
    this.description = description;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user){
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(RoleType.USER.getName()));
    return new UserPrincipal(
      user.getId(), 
      user.getUsername(), 
      user.getEmail(), 
      user.getTelp(), 
      user.getDescription(), 
      user.getPassword(),
      authorities
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return this.id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return this.email;
  }

  public String getTelp() {
    return this.telp;
  }

  public String getDescription() {
    return this.description;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    UserPrincipal that = (UserPrincipal) obj;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", username='" + getUsername() + "'" +
      ", email='" + getEmail() + "'" +
      ", telp='" + getTelp() + "'" +
      ", description='" + getDescription() + "'" +
      ", password='" + getPassword() + "'" +
      ", authorities='" + getAuthorities() + "'" +
      "}";
  }


}