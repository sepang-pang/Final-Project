package com.team6.finalproject.comment.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotLikedYetException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.advice.custom.SelfLikeNotAllowedException;
import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.dto.CommentResponseDto;
import com.team6.finalproject.comment.service.CommentService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/meetings/{meetingId}/comments")
    @ResponseBody
    public List<CommentResponseDto> readAllMeetingComment(@PathVariable Long meetingId) throws NotExistResourceException {
        return commentService.readAllMeetingComment(meetingId);
    }

    // 댓글 생성
    @PostMapping("/comments")
    @ResponseBody
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return commentService.createComment(commentRequestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    @ResponseBody
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, NotOwnedByUserException {
        return commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, NotOwnedByUserException {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok(new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value()));
    }

    // 좋아요
    @PostMapping("/comments/{commentId}/like")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> commentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SelfLikeNotAllowedException, NotExistResourceException {
        return commentService.commentLike(commentId,userDetails.getUser());
    }

    // 좋아요 취소
    @DeleteMapping("/comments/{commentId}/dislike")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> CommentDislike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotOwnedByUserException, NotLikedYetException {
        return commentService.CommentDislike(commentId, userDetails.getUser());
    }
}
