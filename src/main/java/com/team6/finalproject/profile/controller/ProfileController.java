package com.team6.finalproject.profile.controller;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.service.ProfileServiceImpl;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileServiceImpl profileService;

    @PostMapping("/profile")
    public ResponseEntity<ApiResponseDto> createProfile(@RequestBody ProfileRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        profileService.createProfile(requestDto, userDetails);
        return ResponseEntity.ok(new ApiResponseDto("프로필 작성 완료", HttpStatus.OK.value()));
    }

    @GetMapping("/profile")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.getProfile(userDetails);
    }

    @PatchMapping("/profile")
    public ProfileResponseDto updateProfile(@RequestBody ProfileRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.updateProfile(requestDto, userDetails);
    }

    @PatchMapping("/profile/image")
    public ProfileResponseDto updateImage(@RequestPart MultipartFile file,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return profileService.updateImage(file, userDetails);
    }
}
