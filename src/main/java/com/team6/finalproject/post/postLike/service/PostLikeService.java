package com.team6.finalproject.post.postLike.service;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.postLike.entity.PostLike;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface PostLikeService {
    ResponseEntity<ApiResponseDto> postLike(Long postId, User user);

    ResponseEntity<ApiResponseDto> deletePostLike(Long postId, User user);


}
