package com.kulguy.findcomputer.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User extends DateAudit {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 40)
  @Column(name = "name", nullable = false)
  private String name;
  
  @Column(name = "email", nullable = false)
  @Size(max = 40)
  private String email;
  
  @Column(name = "telp", nullable = false)
  @Size(max = 20)
  private String telp;
  
  @NotBlank
  @Size(max = 40)
  @Column(name = "username", nullable = false)
  private String username;

  @NotBlank
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "description", nullable = true)
  private String description;


  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL
  )
  private List<Item> items;

  @OneToMany(
    mappedBy = "userBought",
    cascade = CascadeType.ALL
  )
  private List<Item> items_bought;
  
  @OneToOne
  @JoinColumn(name = "profile_picture_id")
  @Null
  private File profilePicture;

  public User(){}

  public User(String name, String email, String telp, String username, String password, String description, File profilePicture) {
    this.name = name;
    this.email = email;
    this.telp = telp;
    this.username = username;
    this.password = password;
    this.description = description;
    this.profilePicture = profilePicture;
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

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Item> getItems() {
    return this.items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public List<Item> getItems_bought() {
    return this.items_bought;
  }

  public void setItems_bought(List<Item> items_bought) {
    this.items_bought = items_bought;
  }

  public File getProfilePicture() {
    return this.profilePicture;
  }

  public void setProfilePicture(File profilePicture) {
    this.profilePicture = profilePicture;
  }

}