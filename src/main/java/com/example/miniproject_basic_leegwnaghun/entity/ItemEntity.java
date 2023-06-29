package com.example.miniproject_basic_leegwnaghun.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false)
    private String writer;
    private String password;
    private String title;
    private String content;
    private String minPrice;
    private String status;
    private String image;
}
