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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        newComment.setPassword(dto.getPassword());
        newComment = commentRepository.save(newComment);
        return CommentDto.fromEntity(newComment);
    }


//    // GET
//    // readAll
//    public List<CommentDto> readCommentAll(Long id) {
//        List<CommentEntity> commentEntities
//                = commentRepository.findAllByCommentId(id);
//        List<CommentDto> commentList = new ArrayList<>();
//        for (CommentEntity entity: commentEntities) {
//            commentList.add(CommentDto.fromEntity(entity));
//        }
//        return commentList;
//    }

    public Page<CommentDto> readCommentPaged(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageNum, pageSize, Sort.by("commentId").ascending());
        Page<CommentEntity> commentEntityPage = commentRepository.findAll(pageable);
        Page<CommentDto> commentDtoPage = commentEntityPage.map(CommentDto::fromEntity);
        return commentDtoPage;
    }


    // PUT
    // update: 글 수정
    public CommentDto updateComment(
            Long id,
            String password,
            Long commentId,
            CommentDto dto
    ) {
        Optional<CommentEntity> optionalComment
                = commentRepository.findById(commentId);

        CommentEntity commentEntity = optionalComment.get();
        String storedPw = commentEntity.getPassword();

        if (password.equals(storedPw)) {
            if (optionalComment.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            CommentEntity comment = optionalComment.get();

            // 대상 댓글이 대상 게시글의 댓글이 맞는지
            if (!id.equals(comment.getCommentId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

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

        CommentEntity commentEntity = optionalComment.get();
        // 패스워드 저장
        String storedPw = commentEntity.getPassword();

        if (password.equals(storedPw)) {
            commentRepository.deleteById(commentId);
        } else throw new IncorrectPasswordException();

//
    }
}