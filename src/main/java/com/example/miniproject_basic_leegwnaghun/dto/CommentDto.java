package com.example.miniproject_basic_leegwnaghun.dto;


import com.example.miniproject_basic_leegwnaghun.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Long id;
    private Long commentId;
    private String writer;
    private String password;
    private String content;
    private String reply;

    public static CommentDto fromEntity(CommentEntity entity){
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setCommentId(entity.getCommentId());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setContent(entity.getContent());
        dto.setReply(entity.getReply());
        return dto;
    }
}
