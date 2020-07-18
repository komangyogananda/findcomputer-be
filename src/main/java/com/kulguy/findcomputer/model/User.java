package com.kulguy.findcomputer.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

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

  public void addItems(Item item){
    items.add(item);
    item.setUser(this);
  }

  public void removeItems(Item item){
    items.remove(item);
    item.setUser(null);
  }

  public User() {
  }

  public User(String name, String email, String telp, String username, String password, String description) {
    this.name = name;
    this.email = email;
    this.telp = telp;
    this.username = username;
    this.password = password;
    this.description = description;
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

  public User id(Long id) {
    this.id = id;
    return this;
  }

  public User name(String name) {
    this.name = name;
    return this;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  public User telp(String telp) {
    this.telp = telp;
    return this;
  }

  public User username(String username) {
    this.username = username;
    return this;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  public User description(String description) {
    this.description = description;
    return this;
  }

  public User items(List<Item> items) {
    this.items = items;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(telp, user.telp) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(description, user.description) && Objects.equals(items, user.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, telp, username, password, description, items);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", email='" + getEmail() + "'" +
      ", telp='" + getTelp() + "'" +
      ", username='" + getUsername() + "'" +
      ", password='" + getPassword() + "'" +
      ", description='" + getDescription() + "'" +
      ", items='" + getItems() + "'" +
      "}";
  }


}