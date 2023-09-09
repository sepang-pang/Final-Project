package com.team6.finalproject.admin.repoort.service;

import com.team6.finalproject.admin.repoort.dto.ClubReportResponseDto;
import com.team6.finalproject.admin.repoort.dto.ReportApprovalDto;
import com.team6.finalproject.admin.repoort.dto.UserReportResponseDto;
import com.team6.finalproject.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportReceiptService {

    // 신고 승인
    public ResponseEntity<ApiResponseDto> updateReportApprovalState(ReportApprovalDto reportApprovalDto);

    // 유저 신고 전체 조회 (최신순)
    public List<UserReportResponseDto> getUserReportList();

    // 클럽 신고 전체 조회 (최신순)
    public List<ClubReportResponseDto> getClubReportList();

    // 특정 유저가 유저를 신고한 내역 조회
    public List<UserReportResponseDto> getReportListByReportUser(String username);

    // 특정 유저가 클럽을 신고한 내역 조회
    public List<ClubReportResponseDto> getReportListByReportClub(String username);

    // 특정 유저가 신고 당한 내역 조회
    public List<UserReportResponseDto> getReportListByTargetUser(String username);

    // 특정 클럽이 신고 당한 내역 조회
    public List<ClubReportResponseDto> getReportListByTargetClub(String clubName);

}
