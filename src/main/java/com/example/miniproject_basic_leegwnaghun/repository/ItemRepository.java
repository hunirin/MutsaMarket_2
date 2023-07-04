package com.example.miniproject_basic_leegwnaghun.repository;

import com.example.miniproject_basic_leegwnaghun.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    ItemEntity findByWriterAndAndPassword(String writer, String password);
}
