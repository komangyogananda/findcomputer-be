package com.kulguy.findcomputer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_files", uniqueConstraints = {
  @UniqueConstraint(columnNames = {
    "item_id",
    "file_id"
  })
})
public class ItemFile extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "file_id")
  private File file;

  @Column(nullable = true, name = "caption")
  private String caption;

  public ItemFile(@NotNull Item item, @NotNull File file, String caption) {
    this.item = item;
    this.file = file;
    this.caption = caption;
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

  public File getFile() {
    return this.file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getCaption() {
    return this.caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

}