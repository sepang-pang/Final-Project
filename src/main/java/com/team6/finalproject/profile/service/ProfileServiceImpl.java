package com.team6.finalproject.profile.service;

import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.profile.dto.InterestRequestDto;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import com.team6.finalproject.profile.profileinterest.service.ProfileInterestService;
import com.team6.finalproject.profile.repository.ProfileRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final FileUploader fileUploader;
    private final InterestMinorService interestMinorService;
    private final ProfileInterestService profileInterestService;

    // 프로필 생성
    @Override
    @Transactional
    public ProfileResponseDto createProfile(ProfileRequestDto requestDto, User user) {
        Profile profile = Profile.builder()
                .nickname(requestDto.getNickname())
                .introduction(requestDto.getIntroduction())
                .locate(requestDto.getLocate())
                .user(user)
                .build();
        profileRepository.save(profile);
        return new ProfileResponseDto(profile);
    }

    // 자신의 프로필 조회
    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(User user) {
        Profile profile = findProfileByUserId(user.getId());
        return new ProfileResponseDto(profile);
    }

    // 프로필 nickname, introduction 수정
    @Override
    @Transactional
    public ProfileResponseDto updateProfile(ProfileRequestDto requestDto, User user) {
        Profile profile = findProfileByUserId(user.getId());

        String nickname = requestDto.getNickname();
        String introduction = requestDto.getIntroduction();
        String locate = requestDto.getLocate();

        profile.update(nickname, introduction, locate);
        return new ProfileResponseDto(profile);
    }

    // 프로필 이미지 작성/수정
    @Override
    @Transactional
    public ProfileResponseDto updateImage(MultipartFile file, User user) throws IOException {
        Profile profile = findProfileByUserId(user.getId());
        // 수정 시 기존 객체 버킷에서 삭제
        if (profile.getProfileImage() != null) {
            fileUploader.deleteFile(profile.getProfileImage());
        }

        String profileImage = fileUploader.upload(file);
        profile.updateImage(profileImage);
        return new ProfileResponseDto(profile);
    }

    @Override
    @Transactional
    public ProfileResponseDto addInterests(InterestRequestDto requestDto, User user) {
        Profile profile = findProfileByUserId(user.getId());

        List<Long> minorIds = requestDto.getMinorId();
        for (Long minorId : minorIds) {
            InterestMinor interestMinor = interestMinorService.existsInterestMinor(minorId); // 소주제 가져오기
            ProfileInterest profileInterest = ProfileInterest.builder() //// 프로필 관심사 생성
                    .interestMinor(interestMinor)
                    .profile(profile)
                    .build();
            profileInterestService.save(profileInterest); // 프로필 관심사 저장
        }

        return new ProfileResponseDto(profile);
    }

    public Profile findProfileByUserId(Long id) { // 다른 곳에서 호출 필요 시 public으로 열어주세요

        return profileRepository.findByUserId(id).orElseThrow(
                () -> new IllegalArgumentException("프로필을 찾을 수 없습니다."));
    }

    @Override
    public Boolean existValidLocate(Long id) {
        return profileRepository.existValidLocate(id).isEmpty();
    }

    @Override
    public Boolean existValidInterest(Long id) {
        return profileRepository.existValidInterest(id).isEmpty();
    }
}
