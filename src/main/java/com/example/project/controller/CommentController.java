package com.example.project.controller;



import com.example.project.dto.CommentDto;
import com.example.project.dto.ResponseDto;
import com.example.project.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/items/{id}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    // POST
    // 댓글 작성
    @PostMapping
    public ResponseDto create(
            @PathVariable("itemId") Long itemId,
            @RequestBody CommentDto dto
    ) {
        ResponseDto response = new ResponseDto();
        service.createComment(itemId, dto);
        response.setMessage("댓글이 등록되었습니다.");
        return response;
    }

    // GET
    // 댓글 전체 조회
    @GetMapping
    public Page<CommentDto> readAll(
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return service.readCommentPaged(pageNum, pageSize);
    }

    // PUT
    @PutMapping("/{commentId}")
    public ResponseDto update(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto
    ) {
        service.updateComment(dto.getPassword(), commentId, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 수정되었습니다.");
        return response;
    }

    // DELETE
    @DeleteMapping("/{commentId}")
    public ResponseDto delete(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto
    ) {
        service.deleteComment(dto.getPassword(), commentId);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }

    // PUT
    // reply
    @PutMapping("/{commentId}/reply")
    public ResponseDto updateReply(
            @PathVariable("itemId") Long id,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto
    ) {
        service.updateReply(id, dto.getPassword(), commentId, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글에 답변이 추가되었습니다.");
        return response;
    }
}
