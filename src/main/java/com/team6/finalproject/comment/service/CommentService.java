package com.team6.finalproject.comment.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.dto.CommentResponseDto;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) throws NotExistResourceException; // 모임 댓글 작성

    public List<CommentResponseDto> readAllMeetingComment(Long meetingId) throws NotExistResourceException; // 모임 댓글 조회

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) throws NotExistResourceException, NotOwnedByUserException;

    public ResponseEntity<ApiResponseDto> deleteComment(Long commentId, User user) throws NotExistResourceException, NotOwnedByUserException;

    public Comment findComment(Long commentId) throws NotExistResourceException;
}
