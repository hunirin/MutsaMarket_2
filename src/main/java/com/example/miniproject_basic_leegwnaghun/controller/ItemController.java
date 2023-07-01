package com.example.miniproject_basic_leegwnaghun.controller;

import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.dto.ResponseDto;
import com.example.miniproject_basic_leegwnaghun.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    // POST /items
    // 물품 등록
    @PostMapping
    public ResponseDto create(@RequestBody ItemDto itemDto) {
        log.info(itemDto.toString());
        ResponseDto response = new ResponseDto();
        service.createItem(itemDto);



        response.setMessage("등록이 완료되었습니다.");
        return response;
    }

    // GET /items
    // 모두 조회 ( 0 ~ 20개)
    @GetMapping
    public Page<ItemDto> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {
        return service.readItemPaged(page, limit);
    }

    // 하나만 조회
    @GetMapping("/{id}")
    public ItemDto read(
            @PathVariable("id") Long id
    ) {
        return service.readItem(id);
    }

    // PUT /items/{id}
    @PutMapping("/{id}")
    public ItemDto update(
            @PathVariable("id") Long id,
            @RequestBody ItemDto itemDto
    ) {
        return service.updateItem(id, itemDto.getPassword(), itemDto);
    }

    // PUT /users/{id}/image
    // 물품 이미지 추가
    @PutMapping(
            value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ItemDto itemImage(
            @PathVariable("id") Long id,
            @RequestParam("image") MultipartFile itemImage
    ) {
        return service.updateItemImage(id, itemImage);
    }
}
