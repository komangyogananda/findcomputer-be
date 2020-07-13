package com.kulguy.findcomputer.security;

import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username).orElseThrow(
      () -> new UsernameNotFoundException("User not found with username: " + username));
    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id){
    User user = userRepo.findById(id).orElseThrow(
      () -> new UsernameNotFoundException("User not found with id: " + id)
    );
    return UserPrincipal.create(user);
  }

}