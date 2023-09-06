package com.team6.finalproject.user.inquiry.entity;

public enum InquiryTypeEnum {
    CUSTOMER_SUPPORT(InquiryType.CUSTOMER_SUPPORT), // 고객지원
    FEATURE_PROPOSAL(InquiryType.FEATURE_PROPOSAL); // 기능제안

    private final String inquiry;

    InquiryTypeEnum(String inquiry) {
        this.inquiry = inquiry;
    }

    private static class InquiryType {
        public static final String CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";
        public static final String FEATURE_PROPOSAL = "FEATURE_PROPOSAL";
    }
}
