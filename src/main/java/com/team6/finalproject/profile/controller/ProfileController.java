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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileInterestService profileInterestService;
    private final LikeClubService likeClubService;

    @GetMapping("/profile/create")
    public String create() {
        return "manage-profile";
    }

    @GetMapping("/profile/update")
    public String update() {
        return "manage-profile";
    }

    @GetMapping("/my-profile")
    public String getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) throws NotExistResourceException {
        try {
            ProfileResponseDto profileDto = profileService.getProfile(userDetails.getUser());
            model.addAttribute("profileDto", profileDto);
            return "my-profile";
        } catch (NotExistResourceException e) {
            // 프로필이 없을 경우 생성 페이지로 리다이렉트
            return "redirect:/api/profile/create";
        }
    }

    @GetMapping("/profile/{targetId}") // 선택한 프로필 조회
    public String getProfileById(@PathVariable Long targetId, Model model) throws NotExistResourceException {
        ProfileResponseDto profileDto = profileService.getProfileById(targetId);
        model.addAttribute("profileDto", profileDto);
        return "profile";
    }

    @GetMapping("/profile/interests") // 관심사 조회
    public String getInterests() {
        return "manage-interests";

    }

    @PostMapping("/profile") // 프로필 등록
    @ResponseBody
    public ProfileResponseDto createProfile(@RequestPart ProfileRequestDto requestDto, @RequestPart MultipartFile file,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return profileService.createProfile(requestDto, file, userDetails.getUser());
    }

    @PatchMapping("/profile") // 프로필 수정
    @ResponseBody
    public ProfileResponseDto updateProfile(@RequestPart ProfileRequestDto requestDto, @RequestPart MultipartFile file,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, IOException {
        return profileService.updateProfile(requestDto, file, userDetails.getUser());
    }

    @PostMapping("/profile/interests") // 관심사 등록/삭제/재등록
    @ResponseBody
    public ProfileResponseDto inputInterests(@RequestBody InterestRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return profileInterestService.inputInterests(requestDto, userDetails.getUser());
    }

    @PostMapping("/profile/like-club") // 관심 동호회 등록
    @ResponseBody
    public ProfileResponseDto addLikeClub(@RequestBody LikeClubRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, AccessDeniedException {
        return likeClubService.addLikeClub(requestDto, userDetails.getUser());
    }

    @DeleteMapping("/profile/like-club/{clubId}") // 관심 동호회 취소
    @ResponseBody
    public ProfileResponseDto deleteLikeClub(@PathVariable Long clubId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return likeClubService.deleteLikeClub(clubId, userDetails.getUser());
    }
}