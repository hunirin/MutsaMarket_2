package com.example.miniproject_basic_leegwnaghun.service;


import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.entity.ItemEntity;
import com.example.miniproject_basic_leegwnaghun.exceptions.IncorrectPasswordException;
import com.example.miniproject_basic_leegwnaghun.exceptions.ItemNotFoundException;
import com.example.miniproject_basic_leegwnaghun.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        newItem.setStatus("판매중");
        return ItemDto.fromEntity(repository.save(newItem));
    }

    // GET
    // readAll
//    public List<ItemDto> readItemAll() {
//        List<ItemDto> itemList = new ArrayList<>();
//        for (ItemEntity entity: repository.findAll()) {
//            itemList.add(ItemDto.fromEntity(entity));
//        }
//        return itemList;
//    }

    // 페이지 단위로 조회
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


        if (password.equals(storedPw)) {
        itemEntity.setTitle(dto.getTitle());
        itemEntity.setContent(dto.getContent());
        itemEntity.setMinPrice(dto.getMinPrice());
        ItemDto.fromEntity(repository.save(itemEntity));
        return ItemDto.fromEntity(itemEntity);
        } else throw new IncorrectPasswordException();
    }

    // updateImage: 이미지 첨부
    public ItemDto updateItemImage(Long id, String password, MultipartFile itemImage) {
        // 물품 존재 확인
        Optional<ItemEntity> optionalItem = repository.findById(id);
        if (optionalItem.isEmpty()) throw new ItemNotFoundException();

        ItemEntity itemEntity = optionalItem.get();
        // 패스워드 저장
        String storedPw = itemEntity.getPassword();


        if (password.equals(storedPw)) {
            // 업로드위치
            // media/{userId}/image
            String itemImageDir = String.format("media/%d/", id); // 폴더명
            try { // 읽고 쓰는데서 발생할 수 있는 예외 처리
                Files.createDirectories(Path.of(itemImageDir));
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // 확장자를 포함한 이미지 이름
            String originalFilename = itemImage.getOriginalFilename();
            String[] fileNameSplit = originalFilename.split("\\.");
            String extension = fileNameSplit[fileNameSplit.length - 1];
            String itemImageFilename = "item." + extension;
            log.info(itemImageFilename);

            // 폴더와 이미지 이름을 포함한 파일 경로
            String itemImagePath = itemImageDir + itemImageFilename; // 파일 경로
            log.info(itemImagePath);

            // MultipartFile을 저장
            try {
                itemImage.transferTo(Path.of(itemImagePath));
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // ItemEntity 업데이트
            log.info(String.format("/static/%d/%s", id, itemImageFilename));

            itemEntity.setImage(String.format("/static/%d%s", id, itemImageFilename));
            return ItemDto.fromEntity(repository.save(itemEntity));
        } else throw new IncorrectPasswordException();
    }

    // Delete
    // 글삭제
    public void deleteItem(Long id, String password) {
        Optional<ItemEntity> optionalItem = repository.findById(id);
        // 찾지 못하면 물품이 없다고 표시
        if (optionalItem.isEmpty()) throw new ItemNotFoundException();

        ItemEntity itemEntity = optionalItem.get();
        // 패스워드 저장
        String storedPw = itemEntity.getPassword();


        if (password.equals(storedPw)) {
            repository.deleteById(id);
        } else throw new IncorrectPasswordException();
    }

}










