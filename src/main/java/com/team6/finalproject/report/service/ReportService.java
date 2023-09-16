package com.team6.finalproject.report.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.report.dto.MyReportedByClubDto;
import com.team6.finalproject.report.dto.ReportRequestDto;
import com.team6.finalproject.report.dto.MyReportedByUserDto;
import com.team6.finalproject.report.dto.ReportsAgainstMeDto;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ReportService {

    // 신고 접수 (유저)
    public ResponseEntity<ApiResponseDto> submitReportByUser(ReportRequestDto reportRequestDto, User reportUser) throws NotExistResourceException;

    // 신고 접수 (동호회)
    public ResponseEntity<ApiResponseDto> submitReportByClub(ReportRequestDto reportRequestDto, User user) throws NotExistResourceException;

    // 내가 신고한 이력
    public List<MyReportedByUserDto> getMyReportedUserList(User user) throws NotExistResourceException;

    // 내가 신고당한 이력
    public List<ReportsAgainstMeDto> getReportsAgainstMe(User user) throws NotExistResourceException;

    // 내가 클럽을 신고한 이력
    public List<MyReportedByClubDto> getMyReportedClubList(User user) throws NotExistResourceException;

    // 내 클럽이 당한 신고 이력
    public List<ReportsAgainstMeDto> getReportsAgainstMyClub(User user) throws NotExistResourceException;

}
