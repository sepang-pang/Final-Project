package com.team6.finalproject.profile.controller;

import com.team6.finalproject.profile.dto.InterestRequestDto;
import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/profile") // 프로필 등록
    public ProfileResponseDto createProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.createProfile(requestDto, userDetails.getUser());
    }

    @GetMapping("/profile") // 프로필 조회
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.getProfile(userDetails.getUser());
    }

    @PatchMapping("/profile") // 프로필 수정
    public ProfileResponseDto updateProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.updateProfile(requestDto, userDetails.getUser());
    }

    @PatchMapping("/profile/image") // 프로필 이미지 수정
    public ProfileResponseDto updateImage(@RequestPart MultipartFile file,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return profileService.updateImage(file, userDetails.getUser());
    }

    @PostMapping("/profile/interests") // 관심사 등록
    public ProfileResponseDto addInterests(@RequestBody InterestRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.addInterests(requestDto, userDetails.getUser());
    }

    @PostMapping("/profile/like-clubs") // 관심 동호회 등록
    public ProfileResponseDto addLikeClub(@RequestBody LikeClubRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.addLikeClub(requestDto, userDetails.getUser());
    }
}

