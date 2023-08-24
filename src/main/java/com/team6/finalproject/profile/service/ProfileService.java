package com.team6.finalproject.profile.service;

import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileService {
    // 프로필 생성
    void createProfile(ProfileRequestDto requestDto, UserDetailsImpl userDetails);
    // 프로필 조회
    ProfileResponseDto getProfile(UserDetailsImpl userDetails);
    // 프로필 수정
    ProfileResponseDto updateProfile(ProfileRequestDto requestDto, UserDetailsImpl userDetails);
    // 이미지 삽입/수정
    ProfileResponseDto updateImage(MultipartFile file, UserDetailsImpl userDetails) throws IOException;
}
