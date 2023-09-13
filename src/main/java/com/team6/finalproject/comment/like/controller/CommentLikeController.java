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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;


    // 좋아요 여부
    @GetMapping("/{commentId}/like-status")
    @ResponseBody
    public Boolean commentLikeCheck(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return commentLikeService.commentLikeCheck(commentId, userDetails.getUser());
    }
}
