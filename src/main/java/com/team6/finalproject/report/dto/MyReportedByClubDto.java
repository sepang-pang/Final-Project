package com.team6.finalproject.report.dto;

import com.team6.finalproject.report.entity.Report;
import lombok.Getter;

@Getter
public class MyReportedByClubDto {
    private String target; // 신고 대상 이름
    private String title; // 신고 제목
    private String content; // 신고 내용
    private String approvalState; // 신고 처리 상태
    private String reportType; // 신고 유형
    private String createdAt; // 신고 날짜

    public MyReportedByClubDto(Report report) {
        this.target = report.getTargetClub().getName();
        this.title = report.getTitle();
        this.content = report.getContent();
        this.approvalState = report.getApprovalStateEnum().getApprovalState();
        this.reportType = report.getReportTypeEnum().getReportType();
        this.createdAt = report.getCreatedAt().toString();
    }
}
