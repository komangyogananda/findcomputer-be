package com.kulguy.findcomputer.payload;

import java.util.List;
import java.util.Objects;

public class ItemResponse {
  private Long id;
  private String username;
  private String title;
  private String description;
  private String category;
  private Long price;
  private List<String> images;


  public ItemResponse() {
  }

  public ItemResponse(Long id, String username, String title, String description, String category, Long price, List<String> images) {
    this.id = id;
    this.username = username;
    this.title = title;
    this.description = description;
    this.category = category;
    this.price = price;
    this.images = images;
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

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Long getPrice() {
    return this.price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public ItemResponse id(Long id) {
    this.id = id;
    return this;
  }

  public ItemResponse username(String username) {
    this.username = username;
    return this;
  }

  public ItemResponse title(String title) {
    this.title = title;
    return this;
  }

  public ItemResponse description(String description) {
    this.description = description;
    return this;
  }

  public ItemResponse category(String category) {
    this.category = category;
    return this;
  }

  public ItemResponse price(Long price) {
    this.price = price;
    return this;
  }

  public List<String> getImages() {
    return this.images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ItemResponse)) {
            return false;
        }
        ItemResponse itemResponse = (ItemResponse) o;
        return Objects.equals(id, itemResponse.id) && Objects.equals(username, itemResponse.username) && Objects.equals(title, itemResponse.title) && Objects.equals(description, itemResponse.description) && Objects.equals(category, itemResponse.category) && Objects.equals(price, itemResponse.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, title, description, category, price);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", username='" + getUsername() + "'" +
      ", title='" + getTitle() + "'" +
      ", description='" + getDescription() + "'" +
      ", category='" + getCategory() + "'" +
      ", price='" + getPrice() + "'" +
      "}";
  }

}