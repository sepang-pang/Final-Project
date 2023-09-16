package com.team6.finalproject.comment.dto;

import com.team6.finalproject.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String nickname;
    private int likesCount;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.nickname = comment.getNickname();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getCreatedAt();
    }

    public CommentResponseDto(Comment comment, int likesCount) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.nickname = comment.getNickname();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getCreatedAt();
        this.likesCount = likesCount;
    }
}
