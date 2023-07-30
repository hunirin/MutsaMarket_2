package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "proposal")
public class ProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposalId;
    private Long itemId;

    private String username;

    private String status;
    private String suggestedPrice;

    @ManyToOne
    @JoinColumn(name = "proposal")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
