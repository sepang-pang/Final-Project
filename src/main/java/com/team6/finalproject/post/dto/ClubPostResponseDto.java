package com.team6.finalproject.post.dto;

import com.team6.finalproject.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClubPostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private int view;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ClubPostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

    }


}
