package com.kulguy.findcomputer.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "files")
public class File extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @Column(nullable = false, name = "url")
  @NotBlank
  private String url;

  @Column(nullable = false, name = "filename")
  @NotBlank
  private String filename;


  public File() {
  }

  public File(String url, String filename) {
    this.url = url;
    this.filename = filename;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Item getItem() {
    return this.item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public File id(Long id) {
    this.id = id;
    return this;
  }

  public File item(Item item) {
    this.item = item;
    return this;
  }

  public File url(String url) {
    this.url = url;
    return this;
  }

  public String getFilename(){
    return this.filename;
  }

  public void setFilename(String filename){
    this.filename = filename;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof File)) {
            return false;
        }
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(item, file.item) && Objects.equals(url, file.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, item, url);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", item='" + getItem() + "'" +
      ", url='" + getUrl() + "'" +
      "}";
  }
  

  
}