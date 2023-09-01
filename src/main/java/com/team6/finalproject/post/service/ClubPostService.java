package com.team6.finalproject.post.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.dto.ClubPostRequestDto;
import com.team6.finalproject.post.dto.ClubPostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClubPostService {
    List<ClubPostResponseDto> readAllPosts();
    ClubPostResponseDto readPostById(Long postId) throws NotExistResourceException;
    ClubPostResponseDto createPost(ClubPostRequestDto postRequestDto, User user, MultipartFile file) throws IOException, NotExistResourceException;

    ClubPostResponseDto updatePost(Long postId, ClubPostRequestDto postRequestDto, User user, MultipartFile multipartFile) throws IOException, NotOwnedByUserException, NotExistResourceException;

    ResponseEntity<ApiResponseDto> deletePost(Long postId, User user) throws NotExistResourceException, NotOwnedByUserException;

    Post findPost(Long postId) throws NotExistResourceException;
}
