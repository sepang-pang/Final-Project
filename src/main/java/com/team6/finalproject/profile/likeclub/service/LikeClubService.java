package com.team6.finalproject.profile.likeclub.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.user.entity.User;

import java.nio.file.AccessDeniedException;

public interface LikeClubService {
    // 관심 동호회 등록
    ProfileResponseDto addLikeClub(LikeClubRequestDto requestDto, User user) throws NotExistResourceException, AccessDeniedException;
    // 관심 동호회 취소
    ProfileResponseDto deleteLikeClub(Long clubId, User user) throws NotExistResourceException;

    // 관심 동호회 여부 확인
    public boolean isLikeClub(Long clubId, User user) throws NotExistResourceException;
}
