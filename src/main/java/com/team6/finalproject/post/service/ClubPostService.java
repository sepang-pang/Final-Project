package com.team6.finalproject.post.service;

import com.team6.finalproject.post.dto.ClubPostRequestDto;
import com.team6.finalproject.post.dto.ClubPostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClubPostService {
    ClubPostResponseDto getPostById(Long postId);
    ClubPostResponseDto createdPost(ClubPostRequestDto postRequestDto, User user, MultipartFile file) throws IOException;

    ClubPostResponseDto updatePost(Long postId, ClubPostRequestDto postRequestDto, User user, MultipartFile multipartFile) throws IOException;

    void deletePost(Long postId, User user);

    Post findPost(Long postId);

}
