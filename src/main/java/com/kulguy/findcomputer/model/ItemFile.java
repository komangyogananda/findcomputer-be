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

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_files")
public class ItemFile extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter @Getter
  private Long id;

  @NotNull
  @Setter @Getter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @NotNull
  @Setter @Getter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "file_id")
  private File file;

  @Column(nullable = true, name = "caption")
  @Setter @Getter
  private String caption;

  public ItemFile(@NotNull Item item, @NotNull File file, String caption) {
    this.item = item;
    this.file = file;
    this.caption = caption;
  }
}