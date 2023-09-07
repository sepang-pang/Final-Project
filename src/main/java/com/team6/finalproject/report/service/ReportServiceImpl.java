package com.team6.finalproject.report.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.report.dto.MyReportedByClubDto;
import com.team6.finalproject.report.dto.MyReportedByUserDto;
import com.team6.finalproject.report.dto.ReportRequestDto;
import com.team6.finalproject.report.dto.ReportsAgainstMeDto;
import com.team6.finalproject.report.entity.Report;
import com.team6.finalproject.report.enums.ReportTypeEnum;
import com.team6.finalproject.report.repository.ReportRepository;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;
    private final ClubService clubService;

    @Override
    @Transactional // 유저 신고 접수
    public ResponseEntity<ApiResponseDto> submitReportByUser(ReportRequestDto reportRequestDto, User reportUser) throws NotExistResourceException {

        // 신고 대상자 조회
        User targetUser = userService.findByUser(reportRequestDto.getTargetId());

        // 신고자와 신고 대상자가 같은 경우
        if (reportUser.getId().equals(targetUser.getId())) {
            throw new NotExistResourceException("자기 자신을 신고할 수 없습니다.");
        }

        ReportTypeEnum reportType = ReportTypeEnum.USER;

        // 신고 내용 저장
        Report report = Report.builder()
                .reportUser(reportUser)
                .targetUser(targetUser)
                .title(reportRequestDto.getTitle())
                .content(reportRequestDto.getContent())
                .approvalStateEnum(ApprovalStateEnum.PENDING)
                .reportTypeEnum(reportType)
                .build();

        // DB 저장
        reportRepository.save(report);

        return ResponseEntity.ok().body(new ApiResponseDto("신고 접수 완료", 200));
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> submitReportByClub(ReportRequestDto reportRequestDto, User user) throws NotExistResourceException {

        // 신고 대상 동호회 조회
        Club targetClub = clubService.findClub(reportRequestDto.getTargetId());

        // 신고자와 신고 대상 동호회의 관리자가 같은 경우
        if (user.getUsername().equals(targetClub.getUsername())) {
            throw new NotExistResourceException("자신이 관리하는 동호회를 신고할 수 없습니다.");
        }

        ReportTypeEnum reportType = ReportTypeEnum.CLUB;

        // 신고 내용 저장
        Report report = Report.builder()
                .reportUser(user)
                .targetClub(targetClub)
                .title(reportRequestDto.getTitle())
                .content(reportRequestDto.getContent())
                .approvalStateEnum(ApprovalStateEnum.PENDING)
                .reportTypeEnum(reportType)
                .build();

        // DB 저장
        reportRepository.save(report);

        return ResponseEntity.ok().body(new ApiResponseDto("신고 접수 완료", 200));
    }

    @Override
    @Transactional(readOnly = true) // 내가 신고한 이력 조회
    public List<MyReportedByUserDto> getMyReportedUserList(User user) throws NotExistResourceException {
        List<Report> reports = reportRepository.findAllByReportUsernameWithUser(user.getUsername());

        if (reports.isEmpty()) {
            throw new NotExistResourceException("신고 이력이 없습니다.");
        }

        return reports.stream().map(MyReportedByUserDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 내가 신고당한 이력 조회
    public List<ReportsAgainstMeDto> getReportsAgainstMe(User user) throws NotExistResourceException {
        List<Report> reports = reportRepository.findAllByTargetUsernameWithUser(user.getUsername());

        if (reports.isEmpty()) {
            throw new NotExistResourceException("신고 이력이 없습니다.");
        }

        return reports.stream().map(ReportsAgainstMeDto::new).toList();
    }


    @Override
    @Transactional(readOnly = true) // 내가 신고한 클럽 내역 조회
    public List<MyReportedByClubDto> getMyReportedClubList(User user) throws NotExistResourceException {
        List<Report> reports = reportRepository.findAllByReportUsernameWithClub(user.getUsername());

        if (reports.isEmpty()) {
            throw new NotExistResourceException("신고 이력이 없습니다.");
        }

        return reports.stream().map(MyReportedByClubDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 내 클럽이 당한 신고 내역 조회
    public List<ReportsAgainstMeDto> getReportsAgainstMyClub(User user) throws NotExistResourceException {
        List<Report> reports = reportRepository.findAllByTargetUsernameWithClub(user.getUsername());

        if (reports.isEmpty()) {
            throw new NotExistResourceException("신고 이력이 없습니다.");
        }

        return reports.stream().map(ReportsAgainstMeDto::new).toList();
    }

}
