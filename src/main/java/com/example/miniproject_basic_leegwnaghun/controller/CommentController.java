package com.example.miniproject_basic_leegwnaghun.controller;



import com.example.miniproject_basic_leegwnaghun.dto.CommentDto;
import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.dto.ResponseDto;
import com.example.miniproject_basic_leegwnaghun.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items/{id}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    // 게시글 작성
    // POST /articles/{id}/comments
    @PostMapping
    public CommentDto create(
            @PathVariable("id") Long id,
            @RequestBody CommentDto dto
    ) {
        return service.createComment(id, dto);
    }

    // todo 게시글 댓글 전체 조회
    // GET /articles/{id}/comments
    // 반환값 인자 및 필요 어노테이션
    @GetMapping
    public List<CommentDto> readAll(
            @PathVariable("id") Long id
    ) {
        return service.readCommentAll(id);
    }

    // PUT
    @PutMapping("/{commentId}")
    public ResponseDto update(
            @PathVariable("id") Long id,
            @PathVariable("commentID") Long commentId,
            @RequestBody ItemDto itemDto,
            @RequestBody CommentDto dto
    ) {
        service.updateComment(id, itemDto.getPassword(), commentId, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 수정되었습니다.");
        return response;
    }

    // DELETE
    @DeleteMapping("/{commentId}")
    public ResponseDto delete(
            @PathVariable("id") Long id,
            @PathVariable("commentId") Long commentId,
            @RequestBody ItemDto itemDto
    ) {
        service.deleteComment(id, itemDto.getPassword(), commentId);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }
}
