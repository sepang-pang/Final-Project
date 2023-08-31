package com.team6.finalproject.comment.commentLike.controller;

import com.team6.finalproject.comment.commentLike.service.CommentLikeService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs/posts/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 좋아요
    @PostMapping("/{commentId}/like")
    public ResponseEntity<ApiResponseDto> commentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.commentLike(commentId,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("댓글 좋아요 성공", HttpStatus.ACCEPTED.value()));
    }

    // 좋아요 취소
    @DeleteMapping("{commentId}/like")
    public ResponseEntity<ApiResponseDto> deleteCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.deleteCommentLike(commentId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("댓글 좋아요 취소 성공", HttpStatus.ACCEPTED.value()));
    }
}
