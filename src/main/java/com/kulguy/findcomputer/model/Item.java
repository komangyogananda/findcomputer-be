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

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "items")
public class Item extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter 
  @Getter
  private Integer id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @Setter 
  @Getter
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_bought_id")
  @Setter 
  @Getter
  private User userBought;

  @NotBlank
  @Column(nullable = false, name = "title")
  @Setter @Getter
  private String title;

  @Column(nullable = true, name = "description")
  @Setter 
  @Getter
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name="category")
  @Setter @Getter
  private ItemCategory category;

  @Column(nullable = false, name = "price")
  @Setter @Getter
  private Double price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "status")
  @Positive
  @Setter @Getter
  private ItemStatus status;

  @Column(nullable = true, name = "sold_at")
  @Setter 
  @Getter
  private Instant soldAt;


  public Item(){}


  public Item(User user, String title, String description, ItemCategory category, Double price) {
    this.user = user;
    this.title = title;
    this.description = description;
    this.category = category;
    this.price = price;
  }
  
}