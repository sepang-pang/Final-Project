package com.team6.finalproject.comment.service;

import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.dto.CommentResponseDto;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user);

    public List<CommentResponseDto> readAllComment();

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user);

    public ResponseEntity<ApiResponseDto> deleteComment(Long commentId, User user);

    public Comment findComment(Long commentId);
}
