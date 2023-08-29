package com.team6.finalproject.club.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInquiryDto {
    private String nickName;
    private String birth;
    private String introduction;
}
