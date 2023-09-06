package com.team6.finalproject.club.report.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.report.dto.ReportRequestDto;
import com.team6.finalproject.club.report.dto.MyReportedIssuesDto;
import com.team6.finalproject.club.report.dto.ReportsAgainstMeDto;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ReportService {


    // 신고 접수
    public ResponseEntity<ApiResponseDto> submitReport(ReportRequestDto reportRequestDto, User applyUser) throws NotExistResourceException;

    // 내가 신고한 이력
    public List<MyReportedIssuesDto> getMyReportList(User user) throws NotExistResourceException;

    // 내가 신고당한 이력
    public List<ReportsAgainstMeDto> getReportsAgainstMe(User user) throws NotExistResourceException;
}
