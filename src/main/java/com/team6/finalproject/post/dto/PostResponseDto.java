package com.team6.finalproject.post.dto;

import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.post.entity.PostTypeEnum;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private int view;
    private PostTypeEnum postType;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.postType = post.getPostType();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

    }
}
