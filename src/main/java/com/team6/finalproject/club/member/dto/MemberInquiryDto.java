package com.team6.finalproject.club.member.dto;

import com.team6.finalproject.club.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberInquiryDto {
    private Long id;
    private String username;
    private String nickName;
    private String birth;
    private String introduction;
    private String media;

    public MemberInquiryDto(Member member) {
        this.id = member.getUser().getId();
        this.username = member.getUser().getUsername();
        this.nickName = member.getUser().getProfile().getNickname();
        this.birth = member.getUser().getBirth();
        this.introduction = member.getUser().getProfile().getIntroduction();
        this.media = member.getUser().getProfile().getProfileImage();
    }
}
