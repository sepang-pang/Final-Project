package com.team6.finalproject.profile.likeclub.service;

import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.user.entity.User;

public interface LikeClubService {
    // 관심 동호회 등록
    ProfileResponseDto addLikeClub(LikeClubRequestDto requestDto, User user);
}
