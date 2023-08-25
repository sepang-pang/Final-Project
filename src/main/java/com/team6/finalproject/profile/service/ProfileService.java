package com.team6.finalproject.profile.service;

import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileService {
    // 프로필 생성
    ProfileResponseDto createProfile(ProfileRequestDto requestDto, User user);
    // 프로필 조회
    ProfileResponseDto getProfile(User user);
    // 프로필 수정
    ProfileResponseDto updateProfile(ProfileRequestDto requestDto, User user);
    // 이미지 삽입/수정
    ProfileResponseDto updateImage(MultipartFile file, User user) throws IOException;
    // 현재 인가된 유저의 프로필 이름 추출
    Profile findProfileByUserId(Long id);
}
