package com.team6.finalproject.comment.commentLike.service;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CommentLikeService {
    ResponseEntity<ApiResponseDto> commentLike(Long postId, User user);

    ResponseEntity<ApiResponseDto> CommentDislike(Long commentId, User user);


}
