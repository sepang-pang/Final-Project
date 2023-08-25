package com.team6.finalproject.post.service;

import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.PostPersist;

public interface ClubPostService {
    PostResponseDto createdPost(PostRequestDto postRequestDto, User user);

    PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user);

    void deletePost(Long postId, User user);

    Post findPost(Long id);

}
