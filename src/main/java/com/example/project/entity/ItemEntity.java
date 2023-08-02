package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String title;
    private String content;
    private String minPrice;
    private String status;
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany
    @JoinColumn(name = "proposal")
    private List<ProposalEntity> proposals;

    @OneToMany
    @JoinColumn(name = "comment")
    private List<CommentEntity> comments;


}
