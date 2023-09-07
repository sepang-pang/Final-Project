package com.team6.finalproject.report.enums;

import lombok.Getter;

@Getter
public enum ReportTypeEnum {
    USER(ReportTypeEnum.ReportType.USER),
    CLUB(ReportTypeEnum.ReportType.CLUB);

    // 활동 유형 문자열 저장 필드
    private final String reportType;

    ReportTypeEnum(String reportType) {
        this.reportType = reportType;
    }

    private static class ReportType {
        public static final String USER= "USER";
        public static final String CLUB = "CLUB";

    }
}

