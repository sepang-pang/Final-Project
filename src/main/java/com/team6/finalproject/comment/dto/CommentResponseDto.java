package com.team6.finalproject.comment.dto;

import com.team6.finalproject.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String content;
    private String nickname;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.postId = comment.getPost().getPostId();
        this.content = comment.getContent();
        this.nickname = comment.getNickname();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getCreatedAt();
    }
}
