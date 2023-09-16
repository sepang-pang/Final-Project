package com.team6.finalproject.report.repository;

import com.team6.finalproject.report.entity.Report;

import java.util.List;

public interface ReportRepositoryCustom {
    public List<Report> findAllByReportWithUser(); // 유저 신고 전체 최신순 조회 (관리자)
    public List<Report> findAllByReportWithClub(); // 클럽 신고 전체 최신순 조회 (관리자)
    public List<Report> findAllByReportUsernameWithUser(String username);
    public List<Report> findAllByReportUsernameWithClub(String username);
    public List<Report> findAllByTargetUsernameWithUser(String username);
    public List<Report> findAllByTargetUsernameWithClub(String username);
    public List<Report> findAllByTargetClubName(String clubName);

}
