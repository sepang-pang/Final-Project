package com.team6.finalproject.club.enums;

import lombok.Getter;

@Getter
public enum ClubRoleEnum {
    PRESIDENT(ClubRole.PRESIDENT), // 동호회장
    STAFF(ClubRole.STAFF), // 동호회 스태프
    NORMAL(ClubRole.NORMAL); // 동호회 일반 회원

    private final String clubRole;

    ClubRoleEnum(String clubRole) {
        this.clubRole = clubRole;
    }

    public static class ClubRole {
        public static final String PRESIDENT = "PRESIDENT";
        public static final String STAFF = "STAFF";
        public static final String NORMAL = "NORMAL";
    }
}
