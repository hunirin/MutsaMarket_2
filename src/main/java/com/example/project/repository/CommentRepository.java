package com.example.project.repository;

import com.example.project.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<CommentEntity, Long> {

}
