package com.team6.finalproject.profile.profileinterest.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.profile.dto.InterestRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.user.entity.User;

public interface ProfileInterestService {
    // 관심사 등록
    ProfileResponseDto inputInterests(InterestRequestDto requestDto, User user) throws NotExistResourceException;
}
