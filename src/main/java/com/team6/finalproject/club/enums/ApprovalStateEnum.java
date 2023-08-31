package com.team6.finalproject.club.enums;

import lombok.Getter;

@Getter
public enum ApprovalStateEnum {
    PENDING(ApprovalState.PENDING), // 대기
    APPROVE(ApprovalState.APPROVE), // 승인
    REFUSE(ApprovalState.REFUSE); // 거절

    private final String approvalState;

    ApprovalStateEnum(String approvalState) {
        this.approvalState = approvalState;
    }

    private static class ApprovalState {
        public static final String PENDING= "PENDING";
        public static final String APPROVE = "APPROVE";
        public static final String REFUSE = "REFUSE";
    }
}
