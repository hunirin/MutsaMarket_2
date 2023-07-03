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
        dto.setCommentId(entity.getCommentId());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setContent(entity.getContent());
        return dto;
    }

//    public Long getId() {
//        return null;
//    }
//
//    public String getPassword() {
//        return null;
//    }
}
