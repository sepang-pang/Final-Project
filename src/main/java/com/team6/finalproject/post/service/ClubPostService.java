package com.team6.finalproject.post.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClubPostService {
    PostResponseDto getPostById(Long postId);
    PostResponseDto createdPost(PostRequestDto postRequestDto, User user, MultipartFile file) throws IOException;

    PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user, MultipartFile multipartFile) throws IOException;

    void deletePost(Long postId, User user);

    Post findPost(Long postId);

}
