package com.kulguy.findcomputer.model;

public enum RoleType {
  USER("User"),;

  private String name;

  private RoleType(String rolename) {
    this.name = rolename;
  }

  public String getName(){
    return this.name;
  }
}