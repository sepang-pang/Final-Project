package com.team6.finalproject.post.controller;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.dto.ClubPostRequestDto;
import com.team6.finalproject.post.dto.ClubPostResponseDto;
import com.team6.finalproject.post.service.ClubPostService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubPostController {

    private final ClubPostService clubPostService;

    // 모집글 선택조회
    @GetMapping("/posts/{postId}")
    public ClubPostResponseDto getPostById(@PathVariable Long postId) {
        return clubPostService.getPostById(postId);
    }

    // 모집글 생성
    @PostMapping("/posts")
    public ClubPostResponseDto createdPost(@RequestPart ClubPostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart MultipartFile file) throws IOException {
        return clubPostService.createdPost(postRequestDto, userDetails.getUser(), file);
    }

    // 모집글 수정
    @PutMapping("/posts/{postId}")
    public ClubPostResponseDto update(@PathVariable Long postId, @RequestPart ClubPostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart MultipartFile file) throws IOException {
        return clubPostService.updatePost(postId, postRequestDto, userDetails.getUser(), file);
    }

    // 모집글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubPostService.deletePost(postId, userDetails.getUser());
    }


}
