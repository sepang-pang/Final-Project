package com.team6.finalproject.club.entity;

import lombok.Getter;

@Getter
public enum JoinTypeEnum {
    IMMEDIATE(JoinTypeEnum.JoinType.IMMEDIATE),
    APPROVAL(JoinTypeEnum.JoinType.APPROVAL);

    // 활동 유형 문자열 저장 필드
    private final String join;

    JoinTypeEnum(String join) {
        this.join = join;
    }

    private static class JoinType {
        public static final String IMMEDIATE= "ONLINE_JOIN";
        public static final String APPROVAL = "APPROVAL_JOIN";

    }
}
