package com.kulguy.findcomputer.Utils;

import java.util.ArrayList;
import java.util.List;

import com.kulguy.findcomputer.model.Item;
import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.payload.ItemResponse;
import com.kulguy.findcomputer.payload.UserProfile;

public class ModelMapper {
  public static ItemResponse mapItemToItemResponse(Item item){
    List<String> images = new ArrayList<String>();
    item.getImages().forEach(f -> {
      images.add(f.getUrl());
    });
    return new ItemResponse(item.getId(), item.getUser().getUsername(), item.getTitle(), item.getDescription(), item.getCategory().name(), item.getPrice(), images);
  }

  public static UserProfile mapUserToUserProfile(User user){
    return new UserProfile(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getTelp(), user.getDescription(), user.getCreatedAt());
  }
}