package com.example.project.service;



import com.example.project.entity.ItemEntity;
import com.example.project.entity.UserEntity;
import com.example.project.exceptions.IncorrectPasswordException;
import com.example.project.exceptions.ItemNotFoundException;
import com.example.project.repository.CommentRepository;
import com.example.project.dto.CommentDto;
import com.example.project.entity.CommentEntity;
import com.example.project.repository.ItemRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // POST
    // 댓글 생성
    public CommentDto createComment(Long id, String username, String password, CommentDto dto) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {

                if (!itemRepository.existsById(id))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                CommentEntity newComment = new CommentEntity();
                newComment.setContent(dto.getContent());
                newComment.setUsername(storedId);
                newComment = commentRepository.save(newComment);
                return CommentDto.fromEntity(newComment);
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
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
            Long commentId,
            String username,
            String password,
            CommentDto dto
    ) {
        Optional<CommentEntity> optionalComment
                = commentRepository.findById(commentId);

        CommentEntity comment= optionalComment.get();

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {
                if (optionalComment.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                comment.setContent(dto.getContent());
                return CommentDto.fromEntity(commentRepository.save(comment));
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // Delete
    // 글 삭제
    public void deleteComment(Long commentId, String username, String password) {
        Optional<CommentEntity> optionalComment
                = commentRepository.findById(commentId);


        if (optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {
                commentRepository.deleteById(commentId);
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // Reply
    // 답글
    public CommentDto updateReply(
            Long id,
            Long commentId,
            String username,
            String password,
            CommentDto dto
    ) {
        Optional<ItemEntity> optionalItem
                = itemRepository.findById(id);

        ItemEntity itemEntity
                = optionalItem.orElseThrow(() -> new ItemNotFoundException());

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {
                Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);
                CommentEntity comment = optionalComment.orElseThrow(() -> new ItemNotFoundException());

                // 대상 댓글이 대상 게시글의 댓글이 맞는지
                if (!id.equals(comment.getCommentId()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

                comment.setReply(dto.getReply());
                return CommentDto.fromEntity(commentRepository.save(comment));
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

}