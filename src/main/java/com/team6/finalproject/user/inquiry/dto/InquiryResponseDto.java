package com.team6.finalproject.user.inquiry.dto;

import com.team6.finalproject.user.inquiry.entity.Inquiry;
import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryResponseDto {
    private Long inquiryId;
    private Long userId;
    private String username;
    private String title;
    private String inquiryType;
    private String description;
    private String media;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public InquiryResponseDto(Inquiry inquiry) {
        this.inquiryId = inquiry.getId();
        this.userId = inquiry.getUser().getId();
        this.username = inquiry.getUser().getUsername();
        this.title = inquiry.getTitle();
        this.inquiryType = getInquiryTypeString(inquiry.getInquiryType());
        this.description = inquiry.getDescription();
        this.media = inquiry.getMedia();
        this.answer = inquiry.getAnswer();
        this.createdAt = inquiry.getCreatedAt();
        this.modifiedAt = inquiry.getModifiedAt();
    }

    private String getInquiryTypeString(InquiryTypeEnum inquiryTypeEnum) {
        if (inquiryTypeEnum == InquiryTypeEnum.CUSTOMER_SUPPORT) {
            return "고객지원";
        } else {
            return "기능제안";
        }
    }
}
