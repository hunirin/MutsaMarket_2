package com.example.miniproject_basic_leegwnaghun.service;


import com.example.miniproject_basic_leegwnaghun.dto.ItemDto;
import com.example.miniproject_basic_leegwnaghun.entity.ItemEntity;
import com.example.miniproject_basic_leegwnaghun.exceptions.IncorrectPasswordException;
import com.example.miniproject_basic_leegwnaghun.exceptions.ItemNotFoundException;
import com.example.miniproject_basic_leegwnaghun.repository.CommentRepository;
import com.example.miniproject_basic_leegwnaghun.dto.CommentDto;
import com.example.miniproject_basic_leegwnaghun.entity.CommentEntity;
import com.example.miniproject_basic_leegwnaghun.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    // POST
    // 댓글 생성
    public CommentDto createComment(Long id, CommentDto dto) {
        if (!itemRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        CommentEntity newComment = new CommentEntity();
        newComment.setWriter(dto.getWriter());
        newComment.setContent(dto.getContent());
        newComment.setArticleId(id);
        newComment = commentRepository.save(newComment);
        return CommentDto.fromEntity(newComment);
    }


    // GET
    // readAll
    public List<CommentDto> readCommentAll(Long id) {
        List<CommentEntity> commentEntities
                = commentRepository.findAllByArticleId(id);
        List<CommentDto> commentList = new ArrayList<>();
        for (CommentEntity entity: commentEntities) {
            commentList.add(CommentDto.fromEntity(entity));
        }
        return commentList;
    }


    // PUT
    // update: 글 수정
    public CommentDto updateComment(
            Long id,
            String password,
            Long commentId,
            CommentDto dto
    ) {
        // 요청한 댓글이 존재하는지
        Optional<CommentEntity> optionalComment
                = commentRepository.findById(commentId);
        // 존재하지 않으면 예외 발생
        if (optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        CommentEntity comment = optionalComment.get();
        // 패스워드 확인
        Optional<ItemEntity> optionalItem = itemRepository.findById(id);
        ItemEntity itemEntity = optionalItem.get();
        String storedPw = itemEntity.getPassword();

        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!id.equals(comment.getArticleId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (password.equals(storedPw)) {
            comment.setContent(dto.getContent());
            comment.setWriter(dto.getWriter());
            return CommentDto.fromEntity(commentRepository.save(comment));
        } else throw new IncorrectPasswordException();
    }

    // Delete
    // 글 삭제
    public void deleteComment(Long id, String password, Long commentId) {
        Optional<CommentEntity> optionalComment
                = commentRepository.findById(commentId);
        if (optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 패스워드 확인
        Optional<ItemEntity> optionalItem = itemRepository.findById(id);
        // 찾지 못하면 물품이 없다고 표시
        if (optionalItem.isEmpty()) throw new ItemNotFoundException();

        ItemEntity itemEntity = optionalItem.get();
        // 패스워드 저장
        String storedPw = itemEntity.getPassword();

        if (password.equals(storedPw)) {
            commentRepository.deleteById(commentId);
        } else throw new IncorrectPasswordException();

//        CommentEntity comment = optionalComment.get();
//        if (!id.equals(comment.getcommentId()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//
//        commentRepository.deleteById(commentId);
    }
}