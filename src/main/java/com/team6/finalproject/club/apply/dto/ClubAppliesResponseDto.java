package com.team6.finalproject.club.apply.dto;

import com.team6.finalproject.club.apply.entity.ApplyJoinClub;
import lombok.Getter;

@Getter
public class ClubAppliesResponseDto {
    Long id; // 신청 아이디
    String media; // 프로필 사진
    String nickName; // 프로필 이름

    public ClubAppliesResponseDto(ApplyJoinClub applyJoinClub) {
        this.id = applyJoinClub.getId();
        this.media = applyJoinClub.getUser().getProfile().getProfileImage();
        this.nickName = applyJoinClub.getUser().getProfile().getNickname();
    }
}
