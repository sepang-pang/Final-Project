package com.team6.finalproject.admin.repoort.dto;

import com.team6.finalproject.report.entity.Report;
import lombok.Getter;

@Getter
public class UserReportResponseDto { // 수정 필요
    private String title;
    private String content;
    private String reportUser;
    private String targetUser;
    private String approvalState;
    private String createdAt;

    public UserReportResponseDto(Report report) {
        this.title = report.getTitle();
        this.content = report.getContent();
        this.reportUser = report.getReportUser().getUsername();
        this.targetUser = report.getTargetUser().getUsername();
        this.approvalState = report.getApprovalStateEnum().getApprovalState();
        this.createdAt = report.getCreatedAt().toString();
    }
}
