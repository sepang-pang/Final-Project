package com.team6.finalproject.user.inquiry.dto;

import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryRequestDto {
    private Long inquiryId;
    private InquiryTypeEnum inquiryType;
    private String description;
}
