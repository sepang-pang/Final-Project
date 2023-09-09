package com.team6.finalproject.report.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.report.dto.MyReportedByClubDto;
import com.team6.finalproject.report.dto.MyReportedByUserDto;
import com.team6.finalproject.report.dto.ReportRequestDto;
import com.team6.finalproject.report.dto.ReportsAgainstMeDto;
import com.team6.finalproject.report.service.ReportService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 신고 접수 (유저)
    @PostMapping("/user")
    public ResponseEntity<ApiResponseDto> submitReportByUser(@RequestBody ReportRequestDto reportRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.submitReportByUser(reportRequestDto, userDetails.getUser());
    }

    // 신고 접수 (동호회)
    @PostMapping("/club")
    public ResponseEntity<ApiResponseDto> submitReportByClub(@RequestBody ReportRequestDto reportRequestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.submitReportByClub(reportRequestDto, userDetails.getUser());
    }

    // 내가 신고한 이력 조회 (유저)
    @GetMapping("/submitted-by-me")
    public List<MyReportedByUserDto> getMyReportedUserList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.getMyReportedUserList(userDetails.getUser());
    }

    // 내가 신고당한 이력 조회 (유저)
    @GetMapping("/submitted-against-me")
    public List<ReportsAgainstMeDto> getReportsAgainstMe(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.getReportsAgainstMe(userDetails.getUser());
    }

    // 내가 신고한 이력 조회 (동호회)
    @GetMapping("/submitted-by-club")
    public List<MyReportedByClubDto> getMyReportedClubList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.getMyReportedClubList(userDetails.getUser());
    }

    // 내 클럽이 당한 신고 이력 조회
    @GetMapping("/submitted-against-my-club")
    public List<ReportsAgainstMeDto> getReportsAgainstMyClub(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.getReportsAgainstMyClub(userDetails.getUser());
    }
}
