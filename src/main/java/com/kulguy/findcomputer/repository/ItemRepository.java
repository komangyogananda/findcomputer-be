package com.kulguy.findcomputer.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.kulguy.findcomputer.model.Item;
import com.kulguy.findcomputer.model.ItemCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;

public interface ItemRepository extends JpaRepository<Item, Long>{
  Optional<Item> findById(Long itemId);
  Streamable<Item> findByUser_Username(String username);

  Streamable<Item> findByCategoryIn(Collection<ItemCategory> category);

  Streamable<Item> findByTitleContaining(String query);

  @Query(
    value = "from Item it where it.title like %?1% and it.category in ?2"
  )
  Page<Item> findByQueryAndCategory(String query, List<ItemCategory> categories, Pageable pageable);
  
  @Query(
    value = "from Item it where it.title like %?1% and it.category in ?2 and it.user.username = ?3"
  )
  Page<Item> findByQueryAndCategoryAndUsername(String query, List<ItemCategory> categories, String username, Pageable pageable);
}