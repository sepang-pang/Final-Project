package com.team6.finalproject.club.report.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.report.dto.MyReportedIssuesDto;
import com.team6.finalproject.club.report.dto.ReportRequestDto;
import com.team6.finalproject.club.report.dto.ReportsAgainstMeDto;
import com.team6.finalproject.club.report.service.ReportService;
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

    // 신고 접수
    @PostMapping("/submit")
    public ResponseEntity<ApiResponseDto> submitReport(@RequestBody ReportRequestDto reportRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.submitReport(reportRequestDto, userDetails.getUser());
    }

    // 내가 신고한 이력 조회
    @GetMapping("/submitted-by-me")
    public List<MyReportedIssuesDto> getMyReportList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.getMyReportList(userDetails.getUser());
    }

    // 내가 신고당한 이력 조회
    @GetMapping("/submitted-against-me")
    public List<ReportsAgainstMeDto> getReportsAgainstMe(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return reportService.getReportsAgainstMe(userDetails.getUser());
    }
}
