package com.team6.finalproject.club.enums;

import lombok.Getter;

@Getter
public enum JoinTypeEnum {
    IMMEDIATE(JoinType.IMMEDIATE),
    APPROVAL(JoinType.APPROVAL);

    // 활동 유형 문자열 저장 필드
    private final String join;

    JoinTypeEnum(String join) {
        this.join = join;
    }

    private static class JoinType {
        public static final String IMMEDIATE= "즉시 가입";
        public static final String APPROVAL = "가입 승인";

    }
}
