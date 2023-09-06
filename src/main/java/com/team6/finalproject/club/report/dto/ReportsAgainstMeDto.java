package com.team6.finalproject.club.report.dto;

import com.team6.finalproject.club.report.entity.Report;
import lombok.Getter;

@Getter
public class ReportsAgainstMeDto {
    private String title;
    private String content;
    private String createdAt;

    public ReportsAgainstMeDto(Report report) {
        this.title = report.getTitle();
        this.content = report.getContent();
        this.createdAt = report.getCreatedAt().toString();
    }
}
