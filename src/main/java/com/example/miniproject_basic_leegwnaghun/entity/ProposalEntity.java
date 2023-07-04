package com.example.miniproject_basic_leegwnaghun.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "proposal")
public class ProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposalId;
    private Long id;

    @Column(nullable = false)
    private String writer;
    private String status;
    private String suggestedPrice;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
}
