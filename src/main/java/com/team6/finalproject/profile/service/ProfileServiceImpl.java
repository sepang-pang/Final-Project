package com.team6.finalproject.profile.service;

import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.repository.ProfileRepository;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;
    private final FileUploader fileUploader;

    // 프로필 생성
    @Override
    public void createProfile(ProfileRequestDto requestDto, UserDetailsImpl userDetails) {
        Profile profile = Profile.builder()
                .nickname(requestDto.getNickname())
                .introduction(requestDto.getIntroduction())
                .user(userDetails.getUser())
                .build();
        profileRepository.save(profile);
    }

    // 자신의 프로필 조회
    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(UserDetailsImpl userDetails) {
        Profile profile = findProfileByUserId(userDetails.getUser().getId());
        return new ProfileResponseDto(profile);
    }

    // 프로필 nickname, introduction 수정
    @Override
    @Transactional
    public ProfileResponseDto updateProfile(ProfileRequestDto requestDto, UserDetailsImpl userDetails) {
        Profile profile = findProfileByUserId(userDetails.getUser().getId());

        String nickname = requestDto.getNickname();
        String introduction = requestDto.getIntroduction();

        profile.update(nickname, introduction);
        return new ProfileResponseDto(profile);
    }

    // 프로필 이미지 작성/수정
    @Override
    @Transactional
    public ProfileResponseDto updateImage(MultipartFile file, UserDetailsImpl userDetails) throws IOException {
        Profile profile = findProfileByUserId(userDetails.getUser().getId());
        // 수정 시 기존 객체 버킷에서 삭제
        if (profile.getProfileImage() != null) {
            fileUploader.deleteFile(profile.getProfileImage());
        }

        String profileImage = fileUploader.upload(file);
        profile.updateImage(profileImage);
        return new ProfileResponseDto(profile);
    }

    private Profile findProfileByUserId(Long id) { // 다른 곳에서 호출 필요 시 public으로 열어주세요
        return profileRepository.findByUserId(id).orElseThrow(
                () -> new IllegalArgumentException("프로필을 찾을 수 없습니다."));
    }
}
