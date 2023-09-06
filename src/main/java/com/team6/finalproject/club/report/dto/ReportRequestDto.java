package com.team6.finalproject.club.report.dto;

import lombok.Getter;

@Getter
public class ReportRequestDto {
    private Long targetUserId;
    private String title;
    private String content;
}
