package com.team6.finalproject.profile.controller;

import com.team6.finalproject.profile.dto.InterestRequestDto;
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

    @PostMapping("/profile")
    public ProfileResponseDto createProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.createProfile(requestDto, userDetails.getUser());
    }

    @GetMapping("/profile")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.getProfile(userDetails.getUser());
    }

    @PatchMapping("/profile")
    public ProfileResponseDto updateProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.updateProfile(requestDto, userDetails.getUser());
    }

    @PatchMapping("/profile/image")
    public ProfileResponseDto updateImage(@RequestPart MultipartFile file,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return profileService.updateImage(file, userDetails.getUser());
    }

    @PostMapping("/profile/interests")
    public ProfileResponseDto addInterests(@RequestBody InterestRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.addInterests(requestDto, userDetails.getUser());
    }
}

