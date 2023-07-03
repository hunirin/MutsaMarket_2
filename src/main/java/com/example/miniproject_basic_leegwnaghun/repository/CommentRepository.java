package com.example.miniproject_basic_leegwnaghun.repository;

import com.example.miniproject_basic_leegwnaghun.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<CommentEntity, Long> {
    // CommentEntity 중 articleId가
    // id인 CommentEntity 만 반환하는 메소드
    List<CommentEntity> findAllByArticleId(Long id);
}
