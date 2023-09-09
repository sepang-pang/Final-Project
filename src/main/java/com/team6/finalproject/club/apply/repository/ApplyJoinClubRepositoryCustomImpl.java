package com.team6.finalproject.club.apply.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.apply.entity.ApplyJoinClub;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.club.apply.entity.QApplyJoinClub.applyJoinClub;

@Repository
@RequiredArgsConstructor
public class ApplyJoinClubRepositoryCustomImpl implements ApplyJoinClubRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override // 유효한 가입 신청서 조회
    public Optional<ApplyJoinClub> findByActiveId(Long id){
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(applyJoinClub)
                                .where(applyJoinClub.id.eq(id)
                                        .and(applyJoinClub.isDeleted.eq(false)))
                                .fetchOne()
                );
    }

    @Override // 가입 승인 대기 중인 신청서 조회 => 동일한 동호회에 중복 승인을 방지하기 위한 쿼리
    public Optional<ApplyJoinClub> findByPendingApplication(Long userId, Long clubId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(applyJoinClub)
                                .where(applyJoinClub.approvalStateEnum.eq(ApprovalStateEnum.PENDING)
                                        .and(applyJoinClub.user.id.eq(userId))
                                        .and(applyJoinClub.club.id.eq(clubId)))
                                .fetchOne()
                );
    }

    @Override
    public List<ApplyJoinClub> findByActiveClubId(Long clubId) {
        return
                jpaQueryFactory
                        .selectFrom(applyJoinClub)
                        .where(applyJoinClub.club.id.eq(clubId)
                                .and(applyJoinClub.isDeleted.eq(false))
                                .and(applyJoinClub.approvalStateEnum.eq(ApprovalStateEnum.PENDING)))
                        .fetch();
    }
}
