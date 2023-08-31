package com.team6.finalproject.post.postLike.controller;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.postLike.service.PostLikeService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 좋아요
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponseDto> postLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.postLike(postId,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 성공", HttpStatus.ACCEPTED.value()));
    }

    // 좋아요 취소
    @DeleteMapping("{postId}/like")
    public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.deletePostLike(postId,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 취소 성공", HttpStatus.ACCEPTED.value()));
    }
}
