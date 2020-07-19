package com.kulguy.findcomputer.model;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

  @OneToMany(
    mappedBy = "item",
    cascade = CascadeType.ALL
  )
  private List<File> images = new ArrayList<File>();

  public Item() {
  }

  public Item(Long id, User user, String title, String description, ItemCategory category, Long price, ItemStatus status) {
    this.id = id;
    this.user = user;
    this.title = title;
    this.description = description;
    this.category = category;
    this.price = price;
    this.status = status;
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

  public Item id(Long id) {
    this.id = id;
    return this;
  }

  public Item user(User user) {
    this.user = user;
    return this;
  }

  public Item title(String title) {
    this.title = title;
    return this;
  }

  public Item description(String description) {
    this.description = description;
    return this;
  }

  public Item category(ItemCategory category) {
    this.category = category;
    return this;
  }

  public Item price(Long price) {
    this.price = price;
    return this;
  }

  public Item status(ItemStatus status) {
    this.status = status;
    return this;
  }

  public void addImage(File image){
    images.add(image);
    image.setItem(this);
  }

  public void removeImage(File image){
    images.remove(image);
    image.setItem(null);
  }

  public List<File> getImages() {
    return this.images;
  }

  public void setImages(List<File> images) {
    this.images = images;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(user, item.user) && Objects.equals(title, item.title) && Objects.equals(description, item.description) && Objects.equals(category, item.category) && Objects.equals(price, item.price) && Objects.equals(status, item.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, title, description, category, price, status);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", user='" + getUser() + "'" +
      ", title='" + getTitle() + "'" +
      ", description='" + getDescription() + "'" +
      ", category='" + getCategory() + "'" +
      ", price='" + getPrice() + "'" +
      ", status='" + getStatus() + "'" +
      ", images='" + getImages() + "'" +
      "}";
  }
  

  
}