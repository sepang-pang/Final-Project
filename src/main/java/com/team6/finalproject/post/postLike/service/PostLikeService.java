package com.team6.finalproject.post.postLike.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotLikedYetException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.advice.custom.SelfLikeNotAllowedException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.postLike.entity.PostLike;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface PostLikeService {
    ResponseEntity<ApiResponseDto> postLike(Long postId, User user) throws SelfLikeNotAllowedException, NotExistResourceException;

    ResponseEntity<ApiResponseDto> PostDislike(Long postId, User user) throws NotLikedYetException, NotOwnedByUserException;


}
