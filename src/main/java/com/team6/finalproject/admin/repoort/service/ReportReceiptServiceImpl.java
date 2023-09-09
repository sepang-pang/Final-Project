package com.team6.finalproject.admin.repoort.service;

import com.team6.finalproject.admin.repoort.dto.ClubReportResponseDto;
import com.team6.finalproject.admin.repoort.dto.ReportApprovalDto;
import com.team6.finalproject.admin.repoort.dto.UserReportResponseDto;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.report.entity.Report;
import com.team6.finalproject.report.enums.ReportTypeEnum;
import com.team6.finalproject.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportReceiptServiceImpl implements ReportReceiptService {

    private final ReportRepository reportRepository;

    @Override
    @Transactional // 신고 승인
    public ResponseEntity<ApiResponseDto> updateReportApprovalState(ReportApprovalDto reportApprovalDto) {
        Report report = reportRepository.findById(reportApprovalDto.getReportId()).orElseThrow(
                () -> new IllegalArgumentException("해당 신고가 존재하지 않습니다.")
        );

        // PENDING, APPROVE, REFUSE 외 입력값이 들어온 경우 예외 발생
        ApprovalStateEnum approvalStateEnum = reportApprovalDto.getApprovalState();

        if (approvalStateEnum != ApprovalStateEnum.PENDING && approvalStateEnum != ApprovalStateEnum.APPROVE && approvalStateEnum != ApprovalStateEnum.REFUSE) {
            throw new IllegalArgumentException("승인 상태는 PENDING, APPROVE, REFUSE 중 하나여야 합니다.");
        }

        // 신고 상태 변경
        report.updateApprovalState(approvalStateEnum);

        // 신고 대상이 동호회고, 신고 승인이 완료된 경우 활동 점수 차감
        if (approvalStateEnum == ApprovalStateEnum.APPROVE && report.getReportTypeEnum() == ReportTypeEnum.CLUB) {
            report.getTargetClub().updateActivityScore(-20);
        }

        // 신고 상태에 따른 메시지
        String message = switch (approvalStateEnum) {
            case PENDING -> "신고 승인 대기 중";
            case APPROVE -> "신고 승인 완료";
            case REFUSE -> "신고 거절 완료";
        };

        return ResponseEntity.ok().body(new ApiResponseDto(message, 200));
    }

    @Override
    @Transactional(readOnly = true) // 유저 신고 전체 조회 (최신순)
    public List<UserReportResponseDto> getUserReportList() {
        // 신고한 유저가 존재하지 않는 경우 예외 발생
        if (reportRepository.findAllByReportWithUser().isEmpty()) {
            throw new IllegalArgumentException("신고한 유저가 존재하지 않습니다.");
        }
        return reportRepository.findAllByReportWithUser().stream().map(UserReportResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 클럽 신고 전체 조회 (최신순)
    public List<ClubReportResponseDto> getClubReportList() {
        // 신고한 클럽이 존재하지 않는 경우 예외 발생
        if (reportRepository.findAllByReportWithClub().isEmpty()) {
            throw new IllegalArgumentException("신고한 클럽이 존재하지 않습니다.");
        }
        return reportRepository.findAllByReportWithClub().stream().map(ClubReportResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 특정 유저가 유저를 신고한 내역 조회
    public List<UserReportResponseDto> getReportListByReportUser(String username) {
        // 신고한 유저가 존재하지 않는 경우 예외 발생
        if (reportRepository.findAllByReportUsernameWithUser(username).isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 신고한 내역이 존재하지 않습니다.");
        }
        return reportRepository.findAllByReportUsernameWithUser(username).stream().map(UserReportResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 특정 유저가 클럽을 신고한 내역 조회
    public List<ClubReportResponseDto> getReportListByReportClub(String username) {
        // 신고한 유저가 존재하지 않는 경우 예외 발생
        if (reportRepository.findAllByReportUsernameWithClub(username).isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 신고한 내역이 존재하지 않습니다.");
        }
        return reportRepository.findAllByReportUsernameWithClub(username).stream().map(ClubReportResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 특정 유저가 신고 당한 내역 조회
    public List<UserReportResponseDto> getReportListByTargetUser(String username) {
        // 신고 당한 유저가 존재하지 않는 경우 예외 발생
        if (reportRepository.findAllByTargetUsernameWithUser(username).isEmpty()) {
            throw new IllegalArgumentException("해당 유저가 신고 당한 내역이 존재하지 않습니다.");
        }
        return reportRepository.findAllByTargetUsernameWithUser(username).stream().map(UserReportResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true) // 특정 클럽이 신고 당한 내역 조회
    public List<ClubReportResponseDto> getReportListByTargetClub(String clubName) {
        // 신고 당한 클럽이 존재하지 않는 경우 예외 발생
        if (reportRepository.findAllByTargetClubName(clubName).isEmpty()) {
            throw new IllegalArgumentException("해당 클럽이 신고 당한 내역이 존재하지 않습니다.");
        }
        return reportRepository.findAllByTargetClubName(clubName).stream().map(ClubReportResponseDto::new).toList();
    }
}
