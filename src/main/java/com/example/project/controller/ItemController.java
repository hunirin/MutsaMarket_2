package com.example.project.controller;

import com.example.project.dto.ItemDto;
import com.example.project.dto.ResponseDto;
import com.example.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
    public ResponseDto create(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody ItemDto itemDto

    ) {
        log.info(itemDto.toString());
//        UserDto userDto = new UserDto();
        ResponseDto response = new ResponseDto();
        service.createItem(username, password, itemDto);
        response.setMessage("등록이 완료되었습니다.");
        return response;
    }

    // GET /items
    // 모두 조회 ( 0 ~ 20개)
    @GetMapping("/read")
    public Page<ItemDto> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {
        return service.readItemPaged(page, limit);
    }

    // 하나만 조회
    @GetMapping("/read/{id}")
    public ItemDto read(
            @PathVariable("id") Long id
    ) {
        return service.readItem(id);
    }

    // PUT /items/{id}
    @PutMapping("/{id}")
    public ResponseDto update(
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody ItemDto itemDto
    ) {
        service.updateItem(id, username, password, itemDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품이 수정되었습니다.");
        return response;
    }

    // PUT /users/{id}/image
    // 물품 이미지 추가
    @PutMapping(
            value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseDto itemImage(
            @PathVariable("id") Long id,
            // @RequestPart로 image는 파일로,
            // writer와 password는 Content type을 application/json으로 해서 받음
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestPart(value = "image", required = false) MultipartFile itemImage
    ) {

        service.updateItemImage(id, username, password, itemImage);
        ResponseDto response = new ResponseDto();
        response.setMessage("이미지가 등록되었습니다.");
        return response;
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseDto delete(
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {

        service.deleteItem(id, username, password);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }
}
