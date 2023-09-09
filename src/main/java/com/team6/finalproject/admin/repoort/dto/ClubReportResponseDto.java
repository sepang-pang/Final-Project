package com.team6.finalproject.admin.repoort.dto;

import com.team6.finalproject.report.entity.Report;
import lombok.Getter;

@Getter
public class ClubReportResponseDto {
    private String title;
    private String content;
    private String reportUser;
    private String targetClub;
    private String approvalState;
    private String createdAt;

    public ClubReportResponseDto(Report report) {
        this.title = report.getTitle();
        this.content = report.getContent();
        this.reportUser = report.getReportUser().getUsername();
        this.targetClub = report.getTargetClub().getName();
        this.approvalState = report.getApprovalStateEnum().getApprovalState();
        this.createdAt = report.getCreatedAt().toString();
    }
}
