package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private Long itemId;

    private String content;
    private String reply;

    private String username;


    @ManyToOne
    @JoinColumn(name = "comment")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}