package com.team6.finalproject.club.apply.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.apply.entity.ApplyJoinClub;

import java.util.List;


public interface ApplyJoinClubService {
    public void saveApplyJoinClub(ApplyJoinClub apply); // 신청서 DB 저장
    public Boolean hasPendingApplication(Long userId, Long clubId); // 이미 존재하는 신청서 확인 논리 판단
    public ApplyJoinClub findApplication(Long applyId) throws NotExistResourceException; // 신청서 조회
    List<ApplyJoinClub> findClubApplies(Long clubId); // 클럽 신청서 조회
}
