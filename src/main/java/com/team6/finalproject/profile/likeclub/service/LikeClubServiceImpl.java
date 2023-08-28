package com.team6.finalproject.profile.likeclub.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.likeclub.entity.LikeClub;
import com.team6.finalproject.profile.likeclub.repository.LikeClubRepository;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeClubServiceImpl implements LikeClubService {

    private final LikeClubRepository likeClubRepository;
    private final ProfileService profileService;
    private final ClubService clubService;

    @Override
    @Transactional
    public ProfileResponseDto addLikeClub(LikeClubRequestDto requestDto, User user) {
        Profile profile = profileService.findProfileByUserId(user.getId());
        Club club = clubService.findClub(requestDto.getClubId()); // 요청 클럽 담기

        Optional<LikeClub> checkClub = likeClubRepository.findById(club.getId());
        if (club.equals(checkClub)) { // 중복 등록 제한
            throw new IllegalArgumentException("이미 등록된 동호회입니다.");
        }

        LikeClub likeClub = LikeClub.builder()
                .profile(profile)
                .club(club)
                .build();
        likeClubRepository.save(likeClub);
        return new ProfileResponseDto(profile);
    }
}
