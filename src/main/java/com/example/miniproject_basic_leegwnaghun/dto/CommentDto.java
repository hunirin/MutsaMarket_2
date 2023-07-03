package com.example.miniproject_basic_leegwnaghun.dto;


import com.example.miniproject_basic_leegwnaghun.entity.CommentEntity;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long commentId;
    private String writer;
    private String password;
    private String content;

    public static CommentDto fromEntity(CommentEntity entity){
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setCommentId(entity.getArticleId());
        dto.setWriter(entity.getWriter());
        dto.setContent(entity.getContent());
        return dto;
    }
}
