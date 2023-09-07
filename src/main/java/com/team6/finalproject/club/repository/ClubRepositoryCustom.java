package com.team6.finalproject.club.repository;

import com.team6.finalproject.club.entity.Club;

import java.util.List;
import java.util.Optional;

public interface ClubRepositoryCustom {
    public Optional<Club> findByActiveId(Long id);
    public Optional<Club> findByActiveClubName(String name);
    public List<Club> findByActiveInterestMajor(Long majorId); // 관심사 대주제 별로 조회 - 최신순 정렬
    public List<Club> findByActiveInterestMinor(Long minorId); // 관심사 소주제 별로 조회 - 최신순 정렬
    public List<Club> findClubsByUserInterestMinor(List<Long> userInterestIds); // 유저 관심사 별 조회
    public List<Club> findClubsByUserAge(int userAge); // 유저 연령대 별 조회
}