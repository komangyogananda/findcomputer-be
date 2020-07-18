package com.kulguy.findcomputer.service;

import com.kulguy.findcomputer.Utils.ModelMapper;
import com.kulguy.findcomputer.exception.BadRequestException;
import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.payload.UserProfile;
import com.kulguy.findcomputer.payload.UserRequest;
import com.kulguy.findcomputer.repository.UserRepository;
import com.kulguy.findcomputer.security.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public User get(Long id){
    return userRepository.findById(id).get();
  }

  public void save(User user){
    userRepository.save(user);
  }

  public User editUser(UserPrincipal currentUser, UserRequest userRequest){
    if (userRequest.getId() != currentUser.getId()){
      throw new BadRequestException("Bad requests");
    }
    if (userRepository.existsByEmail(userRequest.getEmail())){
      if (userRepository.findByEmail(userRequest.getEmail()).get().getId() != userRequest.getId()){
        throw new BadRequestException("Email already taken");
      }
    }
    User user = userRepository.findById(userRequest.getId()).get();
    user.setName(userRequest.getName());
    user.setEmail(userRequest.getEmail());
    user.setTelp(userRequest.getTelp());
    user.setDescription(userRequest.getDescription());
    userRepository.save(user);
    return user;
  }

  public UserProfile getUserByUsername(String username){
    User user = userRepository.findByUsername(username).get();
    return ModelMapper.mapUserToUserProfile(user);
  }
}