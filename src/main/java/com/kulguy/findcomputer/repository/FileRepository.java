package com.kulguy.findcomputer.repository;

import com.kulguy.findcomputer.model.File;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long>{
  
}