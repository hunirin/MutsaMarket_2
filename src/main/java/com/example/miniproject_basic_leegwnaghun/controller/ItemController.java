package com.example.miniproject_basic_leegwnaghun.controller;

import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.dto.ResponseDto;
import com.example.miniproject_basic_leegwnaghun.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public ItemDto read(@PathVariable("id") Long id) {
        return service.readItem(id);
    }
}
