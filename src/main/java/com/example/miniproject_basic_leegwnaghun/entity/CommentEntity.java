package com.example.miniproject_basic_leegwnaghun.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private Long id;

    @Column(nullable = false)
    private String writer;
    private String content;
    private String reply;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
}