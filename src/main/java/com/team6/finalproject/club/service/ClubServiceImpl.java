package com.team6.finalproject.club.service;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.InterestMajorDto;
import com.team6.finalproject.club.dto.InterestMinorDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "club service 로직")
public class ClubServiceImpl implements ClubService{

    private final ClubRepository clubRepository;
    private final InterestMinorService interestMinorService;

    @Override
    @Transactional
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto, User user) {

        // 소주제 존재 유무 확인
        InterestMinor interestMinor = interestMinorService.existsInterestMinor(clubRequestDto.getMinorId());

        // 동호회 이름 존재 확인
        if(clubRepository.findByName(clubRequestDto.getName()).isPresent()){ // isPresent(): 존재하면 true, 존재하지 않으면 false
            throw new IllegalArgumentException("동호회 이름이 이미 존재합니다.");
        }

        // 가입 방식 설정
        log.info("가입 방식 설정");
        JoinTypeEnum join = JoinTypeEnum.APPROVAL;
        if(clubRequestDto.isOpenJoinType()){
            join = JoinTypeEnum.IMMEDIATE;
        }

        // 활동 방식 설정
        log.info("활동 방식 설정");
        ActivityTypeEnum activity = ActivityTypeEnum.OFFLINE;
        if(clubRequestDto.isOnline()) {
            activity = ActivityTypeEnum.ONLINE;
        }

        // 동호회 개설
        log.info("동호회 개설");
        Club club = Club.builder()
                .username(user.getUsername())
                .name(clubRequestDto.getName())
                .description(clubRequestDto.getDescription())
                .maxMember(clubRequestDto.getMaxMember())
                .activityType(activity)
                .joinType(join)
                .minor(interestMinor)
                .isTrialAvailable(clubRequestDto.isTrialAvailable())
                .build();

        // DB 저장
        log.info("DB 저장");
        clubRepository.save(club);

        // response 반환
        log.info("response 반환");
        return new ClubResponseDto(
                user,
                club,
                new InterestMajorDto(club.getMinor().getInterestMajor()),
                new InterestMinorDto(club.getMinor()));
    }
}