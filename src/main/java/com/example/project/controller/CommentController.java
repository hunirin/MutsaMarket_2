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
            @PathVariable("id") Long itemId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto
    ) {
        ResponseDto response = new ResponseDto();
        service.createComment(itemId, username, password, dto);
        response.setMessage("댓글이 등록되었습니다.");
        return response;
    }

    // GET
    // 댓글 전체 조회
    @GetMapping("/read")
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
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto
    ) {
        service.updateComment(commentId, username, password, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 수정되었습니다.");
        return response;
    }

    // DELETE
    @DeleteMapping("/{commentId}")
    public ResponseDto delete(
            @PathVariable("commentId") Long commentId,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        service.deleteComment(commentId, username, password);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글을 삭제했습니다.");
        return response;
    }

    // PUT
    // reply
    @PutMapping("/{commentId}/reply")
    public ResponseDto updateReply(
            @PathVariable("id") Long id,
            @PathVariable("commentId") Long commentId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto
    ) {
        service.updateReply(id, commentId, username, password, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글에 답변이 추가되었습니다.");
        return response;
    }
}
