package com.team6.finalproject.post.controller;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.service.ClubPostServiceImpl;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubPostController {

    private final ClubPostServiceImpl clubPostService;

    // 모집글 선택조회
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return clubPostService.getPostById(postId);
    }

    // 모집글 생성
    @PostMapping("/posts")
    public PostResponseDto createdPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return clubPostService.createdPost(postRequestDto, userDetails.getUser());
    }

    // 모집글 수정
    @PutMapping("/posts/{postId}")
    public PostResponseDto update(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return clubPostService.updatePost(postId, postRequestDto, userDetails.getUser());
    }

    // 모집글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        clubPostService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.ok(new ApiResponseDto("모집글 삭제 완료", HttpStatus.OK.value()));
    }

}
