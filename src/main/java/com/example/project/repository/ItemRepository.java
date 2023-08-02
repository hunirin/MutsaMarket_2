package com.example.project.repository;

import com.example.project.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Page<ItemEntity> findByTitleContainingOrContentContaining(String searchText, String searchText1, Pageable pageable);
}
