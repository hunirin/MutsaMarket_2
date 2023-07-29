package com.example.project.dto;


import com.example.project.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Long itemId;
    private Long commentId;

    //삭제해야함
    private String writer;
    private String password;

    private String username;
    private String content;
    private String reply;

    public static CommentDto fromEntity(CommentEntity entity){
        CommentDto dto = new CommentDto();
        dto.setItemId(entity.getItemId());
        dto.setCommentId(entity.getCommentId());
        dto.setUsername(entity.getUsername());

        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());

        dto.setContent(entity.getContent());
        dto.setReply(entity.getReply());
        return dto;
    }
}
