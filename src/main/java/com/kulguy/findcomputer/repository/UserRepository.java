package com.kulguy.findcomputer.repository;

import java.util.Optional;

import com.kulguy.findcomputer.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  Boolean existsByUsername(String username);
  Boolean existsByEmail(String username);
}