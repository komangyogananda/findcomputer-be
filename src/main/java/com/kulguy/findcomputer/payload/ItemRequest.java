package com.kulguy.findcomputer.payload;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kulguy.findcomputer.model.ItemCategory;

public class ItemRequest {

  @NotBlank
  @NotNull
  private String category;

  @NotBlank
  @NotNull
  private String title;

  @NotBlank
  @NotNull
  private String description;

  @NotBlank
  @NotNull
  private Long price;

  private List<ImageRequest> images;

  public String getCategory() {
    return this.category;
  }

  public ItemCategory getItemCategory(){
    return ItemCategory.valueOf(this.category);
  }

  public void setCategory(String category) {
    this.category = category;
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

  public Long getPrice() {
    return this.price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public List<ImageRequest> getImages() {
    return this.images;
  }

  public void setImages(List<ImageRequest> images) {
    this.images = images;
  }

  
}