package com.example.miniproject_basic_leegwnaghun.controller;

import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.dto.ResponseDto;
import com.example.miniproject_basic_leegwnaghun.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    // Post /item
    // 물품 등록
    @PostMapping("/items")
    public ResponseDto create(@RequestBody ItemDto itemDto) {
        log.info(itemDto.toString());
        ResponseDto response = new ResponseDto();
        ItemDto item = new ItemDto();
        response.setMessage("등록이 완료되었습니다.");
        item.setStatus("판매중");
        return response;
    }
}
