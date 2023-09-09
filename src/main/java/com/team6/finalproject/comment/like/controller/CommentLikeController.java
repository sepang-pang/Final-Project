package com.team6.finalproject.comment.like.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotLikedYetException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.advice.custom.SelfLikeNotAllowedException;
import com.team6.finalproject.comment.like.service.CommentLikeService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponseDto> commentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SelfLikeNotAllowedException, NotExistResourceException {
        return commentLikeService.commentLike(commentId,userDetails.getUser());
    }

    // 좋아요 취소
    @DeleteMapping("{commentId}/dislike")
    public ResponseEntity<ApiResponseDto> CommentDislike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotOwnedByUserException, NotLikedYetException {
       return commentLikeService.CommentDislike(commentId, userDetails.getUser());
    }
}
