package com.team6.finalproject.profile.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.repository.ProfileRepository;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final FileUploader fileUploader;

    // 프로필 생성
    @Override
    @Transactional
    public ProfileResponseDto createProfile(ProfileRequestDto requestDto, MultipartFile file, User user) throws IOException {
        String profileImage = fileUploader.upload(file);
        Profile profile = Profile.builder()
                .nickname(requestDto.getNickname())
                .introduction(requestDto.getIntroduction())
                .profileImage(profileImage)
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .zoneCode(requestDto.getZoneCode())
                .locate(requestDto.getLocate())
                .user(user)
                .build();
        profileRepository.save(profile);

        user.saveProfile(profile);
        userService.saveUser(user);
        return new ProfileResponseDto(profile);
    }

    // 자신의 프로필 조회
    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(User user) throws NotExistResourceException {
        Profile profile = findProfileByUserId(user.getId());
        return new ProfileResponseDto(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileById(Long profileId) throws NotExistResourceException {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new NotExistResourceException("프로필을 찾을 수 없습니다."));
        return new ProfileResponseDto(profile);
    }

    // 프로필 nickname, introduction, zoneCode, locate 수정
    @Override
    @Transactional
    public ProfileResponseDto updateProfile(ProfileRequestDto requestDto, User user) throws NotExistResourceException {
        Profile profile = findProfileByUserId(user.getId());

        String nickname = requestDto.getNickname();
        String introduction = requestDto.getIntroduction();
        Double latitude = requestDto.getLatitude();
        Double longitude = requestDto.getLongitude();
        String zoneCode = requestDto.getZoneCode();
        String locate = requestDto.getLocate();

        profile.update(nickname, introduction, latitude, longitude, zoneCode, locate);
        return new ProfileResponseDto(profile);
    }

    // 프로필 이미지 등록/수정
    @Override
    @Transactional
    public ProfileResponseDto updateImage(MultipartFile file, User user) throws IOException, NotExistResourceException {
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
    public Profile findProfileByUserId(Long id) throws NotExistResourceException {
        return profileRepository.findByUserId(id).orElseThrow(
                () -> new NotExistResourceException("프로필을 찾을 수 없습니다."));
    }

    @Override // 위치 기입 했는지 확인
    public Boolean existValidLocate(Long id) {
        return profileRepository.existValidLocate(id).isEmpty();
    }

    @Override // 관심사 등록했는지 확인
    public Boolean existValidInterest(Long id) {
        return profileRepository.existValidInterest(id).isEmpty();
    }
}
