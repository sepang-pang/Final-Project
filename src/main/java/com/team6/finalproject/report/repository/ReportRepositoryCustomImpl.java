package com.team6.finalproject.report.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.report.entity.Report;
import com.team6.finalproject.report.enums.ReportTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team6.finalproject.report.entity.QReport.report;


@Repository
@RequiredArgsConstructor
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Report> findAllByReportWithUser() {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.reportTypeEnum.eq(ReportTypeEnum.USER))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Report> findAllByReportWithClub() {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.reportTypeEnum.eq(ReportTypeEnum.CLUB))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override // 유저가 유저를 신고한 신고내역 조회
    public List<Report> findAllByReportUsernameWithUser(String username) {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.reportUser.username.eq(username)
                        .and(report.reportTypeEnum.eq(ReportTypeEnum.USER)))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override // 유저가 동호회를 신고한 신고내역 조회
    public List<Report> findAllByReportUsernameWithClub(String username) {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.reportUser.username.eq(username)
                        .and(report.reportTypeEnum.eq(ReportTypeEnum.CLUB)))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override // 유저가 유저에게 신고당한 신고내역 조회
    public List<Report> findAllByTargetUsernameWithUser(String username) {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.targetUser.username.eq(username)
                        .and(report.reportTypeEnum.eq(ReportTypeEnum.USER)))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override // 본인의 동호회가 신고당한 내역 조회
    public List<Report> findAllByTargetUsernameWithClub(String username) {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.targetClub.username.eq(username)
                        .and(report.reportTypeEnum.eq(ReportTypeEnum.CLUB)))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override // 특정 동호회가 신고당한 내역 조회
    public List<Report> findAllByTargetClubName(String clubName) {
        return jpaQueryFactory
                .selectFrom(report)
                .where(report.targetClub.name.eq(clubName))
                .orderBy(report.createdAt.desc())
                .fetch();
    }

}
