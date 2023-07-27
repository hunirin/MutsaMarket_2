package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private String writer;
    private String title;
    private String content;
    private String minPrice;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column
    private String status;
    private String image;

    @OneToMany
    @JoinColumn(name = "proposal")
    private List<ProposalEntity> proposals;

    @OneToMany
    @JoinColumn(name = "comment")
    private List<CommentEntity> comments;
}
