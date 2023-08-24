package com.team6.finalproject.club.service;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.InterestMajorDto;
import com.team6.finalproject.club.dto.InterestMinorDto;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.repository.InterestMinorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "club service 로직")
public class ClubService {

    private final ClubRepository clubRepository;
    private final InterestMinorRepository interestMinorRepository;

    //동호회 개설
    // 생각할 것 : 동일한 이름의 동호회 개설 제약, 빌더 패턴 도임, 리포지토리 직접 참조 지양, 서비스로직 임플 분리
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto) {
        // 소주제 존재 유무 확인
        InterestMinor interestMinor = interestMinorRepository.findById(clubRequestDto.getMinorId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 종목입니다."));

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
        Club club = new Club(clubRequestDto, interestMinor, join, activity);

        // DB 저장
        log.info("DB 저장");
        clubRepository.save(club);

        // response 반환
        log.info("response 반환");
        return new ClubResponseDto(
                club,
                new InterestMajorDto(club.getMinor().getInterestMajor()),
                new InterestMinorDto(club.getMinor()));
    }

}
