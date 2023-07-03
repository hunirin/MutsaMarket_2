package com.example.miniproject_basic_leegwnaghun.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

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
}
