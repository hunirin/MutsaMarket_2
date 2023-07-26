package com.example.project.repository;


import com.example.project.entity.ProposalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<ProposalEntity, Long> {
}
