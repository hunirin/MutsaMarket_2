package com.example.miniproject_basic_leegwnaghun.service;


import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.entity.ItemEntity;
import com.example.miniproject_basic_leegwnaghun.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    // 글 생성
    public ItemDto createItem(ItemDto dto) {
        ItemEntity newItem = new ItemEntity();
        newItem.setTitle(dto.getTitle());
        newItem.setContent(dto.getContent());
        newItem.setWriter(dto.getWriter());
        newItem.setPassword(dto.getPassword());
        newItem.setMinPrice(dto.getMinPrice());
        return ItemDto.fromEntity(repository.save(newItem));
    }
}
