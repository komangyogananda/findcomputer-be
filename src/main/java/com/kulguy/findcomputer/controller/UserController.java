package com.kulguy.findcomputer.controller;

import javax.validation.Valid;

import com.kulguy.findcomputer.Utils.ModelMapper;
import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.payload.UserProfile;
import com.kulguy.findcomputer.payload.UserRequest;
import com.kulguy.findcomputer.repository.UserRepository;
import com.kulguy.findcomputer.security.CurrentUser;
import com.kulguy.findcomputer.security.UserPrincipal;
import com.kulguy.findcomputer.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/me")
  private UserProfile editUser(@CurrentUser UserPrincipal currentUser){
    User user = userRepository.findById(currentUser.getId()).get();
    return ModelMapper.mapUserToUserProfile(user);
  }

  @GetMapping("/{username}")
  private UserProfile getUser(@PathVariable String username){
    UserProfile userProfile = userService.getUserByUsername(username);
    return userProfile;
  }

  @PutMapping("/me")
  private UserProfile editUser(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UserRequest userRequest){
    logger.info(currentUser.toString());
    logger.info(userRequest.toString());
    User user = userService.editUser(currentUser, userRequest);
    return ModelMapper.mapUserToUserProfile(user);
  }
}