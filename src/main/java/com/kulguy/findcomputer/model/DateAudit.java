package com.kulguy.findcomputer.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
  value = {"createdAt", "updatedAt"},
  allowGetters = true
)
public abstract class DateAudit implements Serializable {
  
  @Setter
  @Getter
  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @Setter
  @Getter
  @LastModifiedDate
  @Column(nullable = false)
  private Instant updatedAt;
}
