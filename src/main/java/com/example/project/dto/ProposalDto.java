package com.example.project.dto;


import com.example.project.entity.ProposalEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalDto {
    private Long itemId;
    private Long proposalId;

    private String username;

    private String suggestedPrice;
    private String status;

    public static ProposalDto fromEntity(ProposalEntity entity) {
        ProposalDto dto = new ProposalDto();
        dto.setItemId(entity.getItemId());
        dto.setProposalId(entity.getProposalId());
        dto.setUsername(entity.getUsername());;
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }

}
