package com.team6.finalproject.club.member.dto;

import com.team6.finalproject.club.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberInquiryDto {
    private String nickName;
    private String birth;
    private String introduction;

    public MemberInquiryDto(Member member) {
        this.nickName = member.getUser().getProfile().getNickname();
        this.birth = member.getUser().getBirth();
        this.introduction = member.getUser().getProfile().getIntroduction();
    }
}
