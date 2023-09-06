package com.team6.finalproject.club.report.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.report.dto.ReportRequestDto;
import com.team6.finalproject.club.report.dto.MyReportedIssuesDto;
import com.team6.finalproject.club.report.dto.ReportsAgainstMeDto;
import com.team6.finalproject.club.report.entity.Report;
import com.team6.finalproject.club.report.repository.ReportRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;

    @Override
    @Transactional // 신고 접수
    public ResponseEntity<ApiResponseDto> submitReport(ReportRequestDto reportRequestDto, User applyUser) throws NotExistResourceException {

        // 신고 대상자 조회
        User targetUser = userService.findByUser(reportRequestDto.getTargetUserId());

        // 신고자와 신고 대상자가 같은 경우
        if (applyUser.getId().equals(targetUser.getId())) {
            throw new NotExistResourceException("자기 자신을 신고할 수 없습니다.");
        }

        // 신고 내용 저장
        Report report = Report.builder()
                .applyUser(applyUser)
                .targetUser(targetUser)
                .title(reportRequestDto.getTitle())
                .content(reportRequestDto.getContent())
                .approvalStateEnum(ApprovalStateEnum.PENDING)
                .build();

        // DB 저장
        reportRepository.save(report);

        return ResponseEntity.ok().body(new ApiResponseDto("신고 접수 완료", 200));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyReportedIssuesDto> getMyReportList(User user) throws NotExistResourceException {
        return getReportList(user, MyReportedIssuesDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportsAgainstMeDto> getReportsAgainstMe(User user) throws NotExistResourceException {
        return getReportList(user, ReportsAgainstMeDto::new);
    }

    private <T> List<T> getReportList(User user, Function<Report, T> converter) throws NotExistResourceException {
        List<Report> reports = reportRepository.findAllById(user.getId());

        if (reports.isEmpty()) {
            throw new NotExistResourceException("신고 이력이 없습니다.");
        }

        return reports.stream().map(converter).toList();
    }
}
