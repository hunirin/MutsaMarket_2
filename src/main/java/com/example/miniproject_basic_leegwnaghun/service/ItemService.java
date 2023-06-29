package com.example.miniproject_basic_leegwnaghun.service;


import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.dto.ResponseDto;
import com.example.miniproject_basic_leegwnaghun.entity.ItemEntity;
import com.example.miniproject_basic_leegwnaghun.exceptions.IncorrectPasswordException;
import com.example.miniproject_basic_leegwnaghun.exceptions.ItemNotFoundException;
import com.example.miniproject_basic_leegwnaghun.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    // POST
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

    // GET
    // readAll: 페이지 단위로 조회
//    public List<ItemDto> readItemAll() {
//        List<ItemDto> itemList = new ArrayList<>();
//        for (ItemEntity entity: repository.findAll()) {
//            itemList.add(ItemDto.fromEntity(entity));
//        }
//        return itemList;
//    }

    // 20개씩 나누어 0번 페이지부터 요청
    public Page<ItemDto> readItemPaged(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageNum, pageSize, Sort.by("id").ascending());
        Page<ItemEntity> itemEntityPage = repository.findAll(pageable);
        Page<ItemDto> itemDtoPage = itemEntityPage.map(ItemDto::fromEntity);
        return itemDtoPage;
    }

    // readOne: 하나만 조회
    public ItemDto readItem(Long id) {
        Optional<ItemEntity> optionalItem = repository.findById(id);
        if (optionalItem.isPresent()) return ItemDto.fromEntity(optionalItem.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // PUT
    // update: 글 수정
    public ItemDto updateItem(Long id, String password, ItemDto dto) {
        Optional<ItemEntity> optionalItem = repository.findById(id);
        // 찾지 못하면 물품이 없다고 표시
        if (optionalItem.isEmpty()) throw new ItemNotFoundException();

        ItemEntity itemEntity = optionalItem.get();
        // 패스워드 저장
        String storedPw = itemEntity.getPassword();
        // 실패 시 메세지
        ResponseDto response = new ResponseDto();

        if (password.equals(storedPw)) {
        itemEntity.setTitle(dto.getTitle());
        itemEntity.setContent(dto.getContent());
        itemEntity.setMinPrice(dto.getMinPrice());
        ItemDto.fromEntity(repository.save(itemEntity));
        return ItemDto.fromEntity(itemEntity);
        } else throw new IncorrectPasswordException();


    }

}










