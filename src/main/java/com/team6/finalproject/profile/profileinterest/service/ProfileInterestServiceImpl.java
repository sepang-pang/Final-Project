package com.team6.finalproject.profile.profileinterest.service;

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

    // 관심사 등록
    @Override
    @Transactional
    public ProfileResponseDto addInterests(InterestRequestDto requestDto, User user) {
        Profile profile = profileService.findProfileByUserId(user.getId());

        List<Long> minorIds = requestDto.getMinorId();
        for (Long minorId : minorIds) {
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
