package com.kulguy.findcomputer.controller;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.google.cloud.storage.Blob;
import com.kulguy.findcomputer.Utils.AppConstants;
import com.kulguy.findcomputer.Utils.ModelMapper;
import com.kulguy.findcomputer.exception.BadRequestException;
import com.kulguy.findcomputer.model.File;
import com.kulguy.findcomputer.model.Item;
import com.kulguy.findcomputer.model.ItemCategory;
import com.kulguy.findcomputer.model.User;
import com.kulguy.findcomputer.payload.ApiResponse;
import com.kulguy.findcomputer.payload.ImageRequest;
import com.kulguy.findcomputer.payload.ItemRequest;
import com.kulguy.findcomputer.payload.ItemResponse;
import com.kulguy.findcomputer.payload.PagedResponse;
import com.kulguy.findcomputer.repository.FileRepository;
import com.kulguy.findcomputer.repository.ItemRepository;
import com.kulguy.findcomputer.repository.UserRepository;
import com.kulguy.findcomputer.security.CurrentUser;
import com.kulguy.findcomputer.security.UserPrincipal;
import com.kulguy.findcomputer.service.ItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/items")
public class ItemController {
  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ItemService itemService;

  @Value("${app.gcpbucketname}")
  private String bucketname;

  private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

  @GetMapping
  public PagedResponse<ItemResponse> getItems(@CurrentUser UserPrincipal currentUser,
                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
  ){
    return itemService.getAllItems(currentUser, page);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<?> createItem(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ItemRequest itemRequest){
    User user = userRepository.findById(currentUser.getId()).get();
    Item item = itemService.createItem(user, itemRequest);
    List<ImageRequest> images = itemRequest.getImages();
    List<String> filenames = new ArrayList<String>();
    
    try {
      if (images != null){
        IntStream.range(0, images.size()).forEach(index -> {
          String filename = itemService.generateImageFilename(currentUser, item, images.get(index));
          itemService.createFile(images.get(index).getBase64(), currentUser, filename);
          String url = itemService.generateGoogleStorageURL(filename);
          File file = new File(url, filename);
          fileRepository.save(file);
          item.addImage(file);
        });   
      }
    } catch (Exception e) {
      filenames.forEach(filename -> {
        itemService.deleteGoogleStorageFile(filename);
      });
      throw new BadRequestException("Failed to upload image");
    }

    URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/items/details/{itemId}")
                .buildAndExpand(item.getId()).toUri();
      
    return ResponseEntity.created(location)
            .body(new ApiResponse(true, "Item created successfully"));
  }

  @PostMapping("/details/{itemId}")
  public ResponseEntity<?> buyItem(@CurrentUser UserPrincipal currentUser, @PathVariable long itemId){
    itemService.buyItem(currentUser, itemId);
    return new ResponseEntity(new ApiResponse(true, "item successfully bought"), HttpStatus.OK);
  }

  @PutMapping("/details/{itemId}")
  public ResponseEntity<?> editItem(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ItemRequest itemRequest, @PathVariable long itemId){
    Item item = itemService.editItem(currentUser, itemRequest, itemId);
    URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/items/details/{itemId}")
              .buildAndExpand(item.getId()).toUri();
    
    return ResponseEntity.created(location)
            .body(new ApiResponse(true, "Item updated successfully"));
  }

  @DeleteMapping("/details/{itemId}")
  public ResponseEntity<?> deleteItem(@CurrentUser UserPrincipal currentUser, @PathVariable long itemId){
    itemService.deleteItem(currentUser, itemId);
    return new ResponseEntity(new ApiResponse(true, "success"), HttpStatus.OK);
  }

  @GetMapping("/details/{itemId}")
  public ItemResponse getItemById(@PathVariable Long itemId){
    Item item = itemService.getItemById(itemId);
    ItemResponse itemResponse = ModelMapper.mapItemToItemResponse(item);
    return itemResponse;
  }

  @GetMapping("/search")
  public PagedResponse<ItemResponse> getItemsQuery(
    @RequestParam(value = "query", defaultValue = "") String query, 
    @RequestParam(value = "username", required = false) String username, 
    @RequestParam(value = "category", defaultValue = "ram,processor,vga,motherboard,storage") List<String> categories,
    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page){
      logger.info(String.format("query = %s, category = %s, page = %s", query, categories.toString(), String.valueOf(page)));
      List<ItemCategory> mappedCategories;
      mappedCategories = categories.stream().map(category -> {
        return ItemCategory.valueOf(category);
      }).collect(Collectors.toList());
    if (username == null){
      return itemService.searchItem(page, query, mappedCategories);
    }else{
      return itemService.searchItem(page, query, mappedCategories, username);
    }

  }

  @GetMapping("/{username}")
  public PagedResponse<ItemResponse> getItemsUser(
    @PathVariable String username,
    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
  ){
    return itemService.getItemsByUsername(username, page);
  }
}