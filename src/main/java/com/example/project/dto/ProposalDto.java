package com.example.project.dto;


import com.example.project.entity.ProposalEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalDto {
    private Long id;
    private Long proposalId;
    private String writer;
    private String password;
    private String suggestedPrice;
    private String status;

    public static ProposalDto fromEntity(ProposalEntity entity) {
        ProposalDto dto = new ProposalDto();
        dto.setId(entity.getId());
        dto.setProposalId(entity.getProposalId());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }

}
