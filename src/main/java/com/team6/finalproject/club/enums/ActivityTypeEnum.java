package com.team6.finalproject.club.enums;

import lombok.Getter;

@Getter
public enum ActivityTypeEnum {
    ONLINE(ActivityType.ONLINE),
    OFFLINE(ActivityType.OFFLINE);

    // 활동 유형 문자열 저장 필드
    private final String activity;

    ActivityTypeEnum(String activity) {
        this.activity = activity;
    }

    private static class ActivityType {
        public static final String ONLINE = "온라인";
        public static final String OFFLINE = "오프라인";

    }
}
