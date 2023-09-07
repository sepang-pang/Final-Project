package com.team6.finalproject.report.dto;

import com.team6.finalproject.report.entity.Report;
import lombok.Getter;

@Getter
public class ReportsAgainstMeDto {
    private String title;
    private String content;
    private String approvalState;
    private String reportType;
    private String createdAt;

    public ReportsAgainstMeDto(Report report) {
        this.title = report.getTitle();
        this.content = report.getContent();
        this.approvalState = report.getApprovalStateEnum().getApprovalState();
        this.reportType = report.getReportTypeEnum().getReportType();
        this.createdAt = report.getCreatedAt().toString();
    }
}
