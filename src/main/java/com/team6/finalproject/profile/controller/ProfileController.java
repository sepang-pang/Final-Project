package com.team6.finalproject.profile.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.profile.dto.InterestRequestDto;
import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.likeclub.service.LikeClubService;
import com.team6.finalproject.profile.profileinterest.service.ProfileInterestService;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileInterestService profileInterestService;
    private final LikeClubService likeClubService;

    @GetMapping("/profile/create")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile") // 프로필 등록
    @ResponseBody
    public ProfileResponseDto createProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.createProfile(requestDto, userDetails.getUser());
    }

    @GetMapping("/profile") // 자신의 프로필 조회
    @ResponseBody
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return profileService.getProfile(userDetails.getUser());
    }

    @GetMapping("/profile/{profileId}") // 선택한 프로필 조회
    @ResponseBody
    public ProfileResponseDto getProfileById(@PathVariable Long profileId) throws NotExistResourceException {
        return profileService.getProfileById(profileId);
    }

    @PatchMapping("/profile") // 프로필 수정
    @ResponseBody
    public ProfileResponseDto updateProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return profileService.updateProfile(requestDto, userDetails.getUser());
    }

    @PatchMapping("/profile/image") // 프로필 이미지 수정
    @ResponseBody
    public ProfileResponseDto updateImage(@RequestPart MultipartFile file,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException, NotExistResourceException {
        return profileService.updateImage(file, userDetails.getUser());
    }

    @PostMapping("/profile/interests") // 관심사 등록
    @ResponseBody
    public ProfileResponseDto addInterests(@RequestBody InterestRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return profileInterestService.addInterests(requestDto, userDetails.getUser());
    }

    @PostMapping("/profile/like-clubs") // 관심 동호회 등록
    @ResponseBody
    public ProfileResponseDto addLikeClub(@RequestBody LikeClubRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return likeClubService.addLikeClub(requestDto, userDetails.getUser());
    }
}