package com.team6.finalproject.post.service;

import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClubPostService {
    PostResponseDto createdPost(PostRequestDto postRequestDto, Long clubId, User user, MultipartFile file) throws IOException;

    PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user);

    void deletePost(Long postId, User user);

//    String uploadMedia(MultipartFile file) throws IOException;

//    PostResponseDto updateMedia(MultipartFile file, Long postId) throws IOException;

    Post findPost(Long postId);

}
