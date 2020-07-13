package com.kulguy.findcomputer.service;

import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
  private UserRepository repo;

  public User get(Long id){
    return repo.findById(id).get();
  }

  public void save(User user){
    repo.save(user);
  }
}