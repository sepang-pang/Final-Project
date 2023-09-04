package com.team6.finalproject.club.apply.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.apply.entity.ApplyJoinClub;
import com.team6.finalproject.club.apply.repository.ApplyJoinClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyJoinClubServiceImpl implements ApplyJoinClubService{
    private final ApplyJoinClubRepository applyJoinClubRepository;

    @Override
    @Transactional
    public void saveApplyJoinClub(ApplyJoinClub apply) { // 신청서 DB 저장
        applyJoinClubRepository.save(apply);
    }
    @Override
    @Transactional(readOnly = true)
    public Boolean hasPendingApplication(Long userId, Long clubId){ // 이미 존재하는 신청서 확인 논리 판단
        return applyJoinClubRepository.findByPendingApplication(userId, clubId).isPresent();
    }
    @Override
    @Transactional(readOnly = true)
    public ApplyJoinClub findApplication(Long applyId) throws NotExistResourceException { // 신청서 조회
        return applyJoinClubRepository.findByActiveId(applyId)
                .orElseThrow(()-> new NotExistResourceException("존재하지 않는 신청입니다."));
    }
}
