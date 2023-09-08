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

    // 관심 동호회 등록
    @Override
    @Transactional
    public ProfileResponseDto addLikeClub(LikeClubRequestDto requestDto, User user) throws NotExistResourceException {
        Profile profile = profileService.findProfileByUserId(user.getId());
        Club club = clubService.findClub(requestDto.getClubId()); // 요청 클럽 담기

        LikeClub likeClub = likeClubRepository.findByClubAndProfile(club, profile);
        if (likeClub == null) {
            // DB에 없는 경우 새로 생성
            LikeClub newlikeClub = LikeClub.builder()
                    .profile(profile)
                    .club(club)
                    .build();
            likeClubRepository.save(newlikeClub);
        } else {
            // isDeleted가 true인 값이 존재하면 isDeleted를 false로 변경
            likeClub.restoreLikeClub();
        }
        return new ProfileResponseDto(profile);
    }

    // 관심 동호회 취소
    @Override
    @Transactional
    public ProfileResponseDto deleteLikeClub(Long clubId, User user) throws NotExistResourceException {
        Club club = clubService.findClub(clubId);
        Profile profile = profileService.findProfileByUserId(user.getId());

        LikeClub likeClub = likeClubRepository.findByClubAndProfile(club, profile);
        likeClub.deleteLikeClub();

        return new ProfileResponseDto(profile);
    }
}
