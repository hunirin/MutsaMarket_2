package com.example.project.service;



import com.example.project.entity.ItemEntity;
import com.example.project.exceptions.IncorrectPasswordException;
import com.example.project.exceptions.ItemNotFoundException;
import com.example.project.repository.CommentRepository;
import com.example.project.dto.CommentDto;
import com.example.project.entity.CommentEntity;
import com.example.project.repository.ItemRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

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

    // GET
    // Page 단위로 조회
    public Page<CommentDto> readCommentPaged(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageNum, pageSize, Sort.by("commentId").ascending());
        Page<CommentEntity> commentEntityPage = commentRepository.findAll(pageable);
        Page<CommentDto> commentDtoPage = commentEntityPage.map(CommentDto::fromEntity);
        return commentDtoPage;
    }

    // PUT
    // update: 댓글 수정
    public CommentDto updateComment(
            String password,
            Long commentId,
            CommentDto dto
    ) {
        Optional<CommentEntity> optionalComment
                = commentRepository.findById(commentId);

        CommentEntity comment= optionalComment.get();
        String storedPw = comment.getPassword();

        if (password.equals(storedPw)) {
            if (optionalComment.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            comment.setContent(dto.getContent());
            comment.setWriter(dto.getWriter());
            return CommentDto.fromEntity(commentRepository.save(comment));
        } else throw new IncorrectPasswordException();
    }

    // Delete
    // 글 삭제
    public void deleteComment(String password, Long commentId) {
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
    }

    // Reply
    // 답글
    public CommentDto updateReply(
            Long id,
            String password,
            Long commentId,
            CommentDto dto
    ) {
        Optional<ItemEntity> optionalItem
                = itemRepository.findById(commentId);

        ItemEntity itemEntity
                = optionalItem.orElseThrow(() -> new ItemNotFoundException());
        String storedPw = itemEntity.getUser().getPassword();

        if (password.equals(storedPw)) {
            Optional<CommentEntity> optionalComment = commentRepository.findById(id);
            CommentEntity comment = optionalComment.orElseThrow(() -> new ItemNotFoundException());

            // 대상 댓글이 대상 게시글의 댓글이 맞는지
            if (!id.equals(comment.getCommentId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            comment.setReply(dto.getReply());
            return CommentDto.fromEntity(commentRepository.save(comment));
        } else throw new IncorrectPasswordException();
    }

}