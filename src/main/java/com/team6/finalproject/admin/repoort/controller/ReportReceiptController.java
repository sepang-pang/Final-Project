package com.team6.finalproject.admin.repoort.controller;

import com.team6.finalproject.admin.repoort.dto.ClubReportResponseDto;
import com.team6.finalproject.admin.repoort.dto.UserReportResponseDto;
import com.team6.finalproject.admin.repoort.dto.ReportApprovalDto;
import com.team6.finalproject.admin.repoort.service.ReportReceiptService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured(UserRoleEnum.Authority.ADMIN)
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReportReceiptController {
    private final ReportReceiptService reportReceiptService;

    @PatchMapping("/report") // 신고 상태 변경
    public ResponseEntity<ApiResponseDto> updateReportApprovalState(@RequestBody ReportApprovalDto reportApprovalDto) {
        return reportReceiptService.updateReportApprovalState(reportApprovalDto);
    }

    @GetMapping("/report/user") // 유저 신고 전체 조회
    public List<UserReportResponseDto> getReportList() {
        return reportReceiptService.getUserReportList();
    }

    @GetMapping("/report/club") // 클럽 신고 전체 조회
    public List<ClubReportResponseDto> getClubReportList() {
        return reportReceiptService.getClubReportList();
    }

    @GetMapping("/report/report-user") // 특정 유저가 유저를 신고한 내역 조회 (완)
    public List<UserReportResponseDto> getReportListByReportUser(@RequestParam("username") String username) {
        return reportReceiptService.getReportListByReportUser(username);
    }

    @GetMapping("/report/target-user") // 특정 유저가 신고 당한 내역 조회 (완)
    public List<UserReportResponseDto> getReportListByTargetUser(@RequestParam("username") String username) {
        return reportReceiptService.getReportListByTargetUser(username);
    }

    @GetMapping("/report/report-club") // 특정 유저가 클럽을 신고한 내역 조회
    public List<ClubReportResponseDto> getReportListByReportClub(@RequestParam("username") String username) {
        return reportReceiptService.getReportListByReportClub(username);
    }
    @GetMapping("/report/target-club")   // 특정 클럽이 신고 당한 내역 조회
    public List<ClubReportResponseDto> getReportListByTargetClub(@RequestParam("clubName") String clubName) {
        return reportReceiptService.getReportListByTargetClub(clubName);
    }

}
