package com.kulguy.findcomputer.service;

import com.kulguy.findcomputer.repository.ItemRepository;
import com.kulguy.findcomputer.repository.UserRepository;
import com.kulguy.findcomputer.security.UserPrincipal;

import java.io.FileInputStream;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import com.kulguy.findcomputer.Utils.AppConstants;
import com.kulguy.findcomputer.Utils.ModelMapper;
import com.kulguy.findcomputer.exception.BadRequestException;
import com.kulguy.findcomputer.model.Item;
import com.kulguy.findcomputer.model.ItemCategory;
import com.kulguy.findcomputer.model.ItemStatus;
import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.payload.ImageRequest;
import com.kulguy.findcomputer.payload.ItemRequest;
import com.kulguy.findcomputer.payload.ItemResponse;
import com.kulguy.findcomputer.payload.PagedResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemService {
  @Autowired
  private ItemRepository itemRepository;

  @Autowired UserRepository userRepository;

  @Value("${app.projectid}")
  private String projectId;

  @Value("${app.gcpbucketname}")
  private String bucketname;

  private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

  public String generateImageFilename(UserPrincipal currentUser, Item item, ImageRequest imageRequest){
    Instant time = Instant.now();
    String filename = String.format("%s_item_%s_time_%s_file_%s", currentUser.getUsername(), item.getId(), time.toEpochMilli(), imageRequest.getFilename());
    return filename;
  }

  public String generateGoogleStorageURL(String filename){
    String url = String.format("https://storage.cloud.google.com/%s/%s", bucketname, filename);
    return url;
  }

  public void deleteGoogleStorageFile(String filename){
    try {
      Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("google-services.json"));
      Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build().getService();
      storage.delete(bucketname, filename);
    } catch (Exception e) {
      
    }
  }

  public PagedResponse<ItemResponse> getAllItems(UserPrincipal currentUser, int page){
    validatePageNumber(page);

    Pageable pageable = PageRequest.of(page, AppConstants.PAGE_SIZE, Sort.Direction.DESC, "createdAt");
    Page<Item> items = itemRepository.findAll(pageable);

    if (items.getSize() == 0){
      return new PagedResponse<>(Collections.emptyList(), items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());
    }
    List<ItemResponse> itemResponses = items.map(item -> {
      return ModelMapper.mapItemToItemResponse(item);
    }).getContent();
    return new PagedResponse<>(itemResponses, items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());
  }

  public PagedResponse<ItemResponse> getItemsByUsername(String username, int page){
    validatePageNumber(page);

    List<ItemResponse> items = itemRepository.findByUser_Username(username).map(item -> {
      return ModelMapper.mapItemToItemResponse(item);
    }).toList();
    PagedListHolder<ItemResponse> pagedItems = new PagedListHolder<ItemResponse>(items);
    pagedItems.setPageSize(AppConstants.PAGE_SIZE);
    pagedItems.setPage(page);
    
    if (items.size() == 0 || page > pagedItems.getPage()){
      return new PagedResponse<>(Collections.emptyList(), pagedItems.getPage(), pagedItems.getPageSize(), items.size(), pagedItems.getPageCount(), pagedItems.isLastPage());
    }
    List<ItemResponse> itemResponses = pagedItems.getPageList();
    return new PagedResponse<>(itemResponses, pagedItems.getPage(), pagedItems.getPageSize(), items.size(), pagedItems.getPageCount(), pagedItems.isLastPage());

  }
  
  public Item getItemById(Long itemId){
    return itemRepository.findById(itemId).get();
  }

  public Item editItem(UserPrincipal currentUser, ItemRequest itemRequest ,Long itemId){
    Item item = itemRepository.findById(itemId).get();
    if (item.getUser().getId() != currentUser.getId()){
      throw new BadRequestException("Invalid user");
    }
    item.setTitle(itemRequest.getTitle());
    item.setPrice(itemRequest.getPrice());
    item.setDescription(itemRequest.getDescription());
    item.setCategory(itemRequest.getItemCategory());
    itemRepository.save(item);
    return item;
  }

  public void deleteItem(UserPrincipal currentUser, Long itemId){
    Item item = itemRepository.findById(itemId).get();
    if (item.getUser().getId() != currentUser.getId()){
      throw new BadRequestException("Invalid user");
    }
    User user = userRepository.findById(currentUser.getId()).get();
    user.removeItems(item);
    item.getImages().forEach(image -> {
      this.deleteGoogleStorageFile(image.getFilename());
    });
    itemRepository.delete(item);
  }

  public void buyItem(UserPrincipal currentUser, Long itemId){
    Item item = itemRepository.findById(itemId).get();
    if (item.getUser().getId() == currentUser.getId()){
      throw new BadRequestException("Invalid user");
    }
    User user = userRepository.findById(currentUser.getId()).get();
    user.removeItems(item);
    itemRepository.delete(item);
  }

  public Item createItem(User user, ItemRequest itemRequest){
    Item item = new Item();
    item.setTitle(itemRequest.getTitle());
    item.setCategory(itemRequest.getItemCategory());
    item.setPrice(itemRequest.getPrice());
    item.setDescription(itemRequest.getDescription());
    item.setStatus(ItemStatus.AVAILABLE);
    user.addItems(item);
    item = itemRepository.save(item);
    return item;
  }

  public Blob createFile(String imageValue, UserPrincipal currentUser, String filename){
    int startPoint = imageValue.indexOf(",");
    if (startPoint > -1) {
        imageValue = imageValue.substring(startPoint + 1);
    }
    byte[] imageByte=Base64.decodeBase64(imageValue);
    try {
      Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("google-services.json"));
      Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build().getService();
      Bucket bucket = storage.get(bucketname);
      Blob blob = bucket.create(filename, imageByte);
      return blob;
    } catch (Exception e) {
      throw new BadRequestException("Failed to upload file");
    }
  }

  public PagedResponse<ItemResponse> searchItem(int page, String queryString, List<ItemCategory> categories){
    validatePageNumber(page);
    Pageable pageable = PageRequest.of(page, AppConstants.PAGE_SIZE, Sort.Direction.DESC, "createdAt");
    Page<Item> items = itemRepository.findByQueryAndCategory(queryString, categories, pageable);
    
    if (items.getSize() == 0){
      return new PagedResponse<>(Collections.emptyList(), items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());
    }
    List<ItemResponse> itemResponses = items.map(item -> {
      return ModelMapper.mapItemToItemResponse(item);
    }).getContent();
    return new PagedResponse<>(itemResponses, items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());
  }
  
  public PagedResponse<ItemResponse> searchItem(int page, String queryString, List<ItemCategory> categories, String username){
    validatePageNumber(page);
    Pageable pageable = PageRequest.of(page, AppConstants.PAGE_SIZE, Sort.Direction.DESC, "createdAt");
    Page<Item> items = itemRepository.findByQueryAndCategoryAndUsername(queryString, categories, username, pageable);
    
    if (items.getSize() == 0){
      return new PagedResponse<>(Collections.emptyList(), items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());
    }
    List<ItemResponse> itemResponses = items.map(item -> {
      return ModelMapper.mapItemToItemResponse(item);
    }).getContent();
    return new PagedResponse<>(itemResponses, items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());
  }

  private void validatePageNumber(int page){
    if (page < 0){
      throw new BadRequestException("Page number invalid");
    }
  }
}