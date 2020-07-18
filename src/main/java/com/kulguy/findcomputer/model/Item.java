package com.kulguy.findcomputer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.Instant;

@Entity
@Table(name = "items")
public class Item extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_bought_id")
  private User userBought;

  @NotBlank
  @Column(nullable = false, name = "title")
  private String title;

  @Column(nullable = true, name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name="category")
  private ItemCategory category;

  @Column(nullable = false, name = "price")
  @Positive
  private Long price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "status")
  private ItemStatus status;

  @Column(nullable = true, name = "sold_at")
  private Instant soldAt;


  public Item(){}

  public Item(User user, String title, String description, ItemCategory category, Long price) {
    this.user = user;
    this.title = title;
    this.description = description;
    this.category = category;
    this.price = price;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUserBought() {
    return this.userBought;
  }

  public void setUserBought(User userBought) {
    this.userBought = userBought;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ItemCategory getCategory() {
    return this.category;
  }

  public void setCategory(ItemCategory category) {
    this.category = category;
  }

  public Long getPrice() {
    return this.price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public ItemStatus getStatus() {
    return this.status;
  }

  public void setStatus(ItemStatus status) {
    this.status = status;
  }

  public Instant getSoldAt() {
    return this.soldAt;
  }

  public void setSoldAt(Instant soldAt) {
    this.soldAt = soldAt;
  }

  
}