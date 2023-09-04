package com.team6.finalproject.post.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.dto.ClubPostRequestDto;
import com.team6.finalproject.post.dto.ClubPostResponseDto;
import com.team6.finalproject.post.service.ClubPostService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubPostController {

    private final ClubPostService clubPostService;

    // 모집글 전체조회
    @GetMapping("/posts")
    public List<ClubPostResponseDto> readAllPosts() {
        return clubPostService.readAllPosts();
    }

    // 모집글 선택조회
    @GetMapping("/posts/{postId}")
    public ClubPostResponseDto getPostById(@PathVariable Long postId) throws NotExistResourceException {
        return clubPostService.readPostById(postId);
    }

    // 모집글 생성
    @PostMapping("/posts")
    public ClubPostResponseDto createPost(@RequestPart ClubPostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart MultipartFile file) throws IOException, NotExistResourceException {
        return clubPostService.createPost(postRequestDto, userDetails.getUser(), file);
    }

    // 모집글 수정
    @PutMapping("/posts/{postId}")
    public ClubPostResponseDto updatePost(@PathVariable Long postId, @RequestPart ClubPostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart MultipartFile file) throws IOException, NotExistResourceException, NotOwnedByUserException {
        return clubPostService.updatePost(postId, postRequestDto, userDetails.getUser(), file);
    }

    // 모집글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, NotOwnedByUserException {
        return clubPostService.deletePost(postId, userDetails.getUser());
    }


}
