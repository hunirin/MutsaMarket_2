package com.example.project.dto;

import com.example.project.entity.ItemEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private String minPrice;
    private String status;
    private String image;

    public static ItemDto fromEntity(ItemEntity entity) {
        ItemDto dto = new ItemDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setMinPrice(entity.getMinPrice());
        dto.setStatus(entity.getStatus());
        dto.setImage(entity.getImage());
        return dto;
    }


}
