package com.team6.finalproject.user.dto;

import com.team6.finalproject.user.inquiry.entity.Inquiry;
import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryResponseDto {
    private String description;
    private InquiryTypeEnum inquiryType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public InquiryResponseDto(Inquiry inquiry) {
        this.description = inquiry.getDescription();
        this.inquiryType = inquiry.getInquiryType();
        this.createdAt = inquiry.getCreatedAt();
        this.modifiedAt = inquiry.getModifiedAt();
    }
}
