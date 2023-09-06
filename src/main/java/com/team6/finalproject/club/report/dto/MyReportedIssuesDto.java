package com.team6.finalproject.club.report.dto;

import com.team6.finalproject.club.report.entity.Report;

public class MyReportedIssuesDto {
    private Long applyUserId;
    private Long targetUserId;
    private String title;
    private String content;
    private String approvalState;
    private String createdAt;

    public MyReportedIssuesDto(Report report) {
        this.applyUserId = report.getApplyUser().getId();
        this.targetUserId = report.getTargetUser().getId();
        this.title = report.getTitle();
        this.content = report.getContent();
        this.approvalState = report.getApprovalStateEnum().getApprovalState();
        this.createdAt = report.getCreatedAt().toString();
    }
}
