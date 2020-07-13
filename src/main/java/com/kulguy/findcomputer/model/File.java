package com.kulguy.findcomputer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "files")
public class File extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter @Getter
  private Long id;

  @Column(nullable = false, name = "url")
  @NotBlank
  @Setter @Getter
  private String url;

  public File() {
  }

  public File(String url) {
    this.url = url;
  }

  
}