package com.kulguy.findcomputer.controller;

import java.net.URI;

import javax.validation.Valid;

import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.payload.ApiResponse;
import com.kulguy.findcomputer.payload.JwtAuthenticationResponse;
import com.kulguy.findcomputer.payload.LoginRequest;
import com.kulguy.findcomputer.payload.SignUpRequest;
import com.kulguy.findcomputer.repository.UserRepository;
import com.kulguy.findcomputer.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepo;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
    logger.info(String.format("%s %s authenticateUser", loginRequest.getUsername(), loginRequest.getPassword()));
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );
    // logger.info(authentication.getCredentials().toString());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
    logger.info(String.format("%s %s register", signUpRequest.getUsername(), signUpRequest.getPassword()));
    if (userRepo.existsByUsername(signUpRequest.getUsername())){
      return new ResponseEntity(new ApiResponse(false, "Username is already taken"), HttpStatus.BAD_REQUEST);
    }
    if (userRepo.existsByEmail(signUpRequest.getEmail())){
      return new ResponseEntity(new ApiResponse(false, "Email is already used"), HttpStatus.BAD_REQUEST);
    }

    User user = new User(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getTelp(), signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getDescription());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User result = userRepo.save(user);

    URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
  }

  
}