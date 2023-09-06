package com.team6.finalproject.profile.likeclub.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
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

@Service
@RequiredArgsConstructor
public class LikeClubServiceImpl implements LikeClubService {

    private final LikeClubRepository likeClubRepository;
    private final ProfileService profileService;
    private final ClubService clubService;

    @Override
    @Transactional
    public ProfileResponseDto addLikeClub(LikeClubRequestDto requestDto, User user) throws NotExistResourceException {
        Profile profile = profileService.findProfileByUserId(user.getId());
        Club club = clubService.findClub(requestDto.getClubId()); // 요청 클럽 담기

        LikeClub likeClub = LikeClub.builder()
                .profile(profile)
                .club(club)
                .build();
        likeClubRepository.save(likeClub);
        return new ProfileResponseDto(profile);
    }
}
