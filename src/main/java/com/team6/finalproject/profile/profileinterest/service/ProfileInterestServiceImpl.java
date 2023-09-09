package com.team6.finalproject.profile.profileinterest.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.profile.dto.InterestRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import com.team6.finalproject.profile.profileinterest.repository.ProfileInterestRepository;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileInterestServiceImpl implements ProfileInterestService {

    private final ProfileInterestRepository profileInterestRepository;
    private final ProfileService profileService;
    private final InterestMinorService interestMinorService;

    // 관심사 등록/수정/제거
    @Override
    @Transactional
    public ProfileResponseDto inputInterests(InterestRequestDto requestDto, User user) throws NotExistResourceException {
        Profile profile = profileService.findProfileByUserId(user.getId());

        // 현재 프로필의 모든 관심사 가져오기
        List<ProfileInterest> currentInterests = profileInterestRepository.findByProfile(profile);

        // 선택한 관심사 목록 가져오기
        List<Long> selectedMinorIds = requestDto.getMinorId();

        // 선택한 관심사 목록 중에 이미 등록되어 있는 것들을 처리
        for (ProfileInterest profileInterest : currentInterests) {
            Long interestMinorId = profileInterest.getInterestMinor().getId();

            if (selectedMinorIds.contains(interestMinorId)) {
                // 선택한 관심사 목록에 이미 있는 경우, isDeleted를 false로 설정하고 목록에서 제거
                profileInterest.restoreInterest();
                selectedMinorIds.remove(interestMinorId);
            } else {
                // 선택한 관심사 목록에 없는 경우, isDeleted를 true로 설정
                profileInterest.deleteInterest();
            }
        }

        // 선택한 관심사 목록 중 남아있는 것들을 등록
        for (Long minorId : selectedMinorIds) {
            InterestMinor interestMinor = interestMinorService.existsInterestMinor(minorId); // 소주제 가져오기
            ProfileInterest profileInterest = ProfileInterest.builder() // 프로필 관심사 생성
                    .interestMinor(interestMinor)
                    .profile(profile)
                    .build();
            profileInterestRepository.save(profileInterest); // 프로필 관심사 저장
        }
        return new ProfileResponseDto(profile);
    }
}
