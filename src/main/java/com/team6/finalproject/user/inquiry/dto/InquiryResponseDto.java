package com.team6.finalproject.user.inquiry.dto;

import com.team6.finalproject.user.inquiry.entity.Inquiry;
import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryResponseDto {
    private String username;
    private InquiryTypeEnum inquiryType;
    private String description;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public InquiryResponseDto(Inquiry inquiry) {
        this.username = inquiry.getUser().getUsername();
        this.inquiryType = inquiry.getInquiryType();
        this.description = inquiry.getDescription();
        this.answer = inquiry.getAnswer();
        this.createdAt = inquiry.getCreatedAt();
        this.modifiedAt = inquiry.getModifiedAt();
    }
}
