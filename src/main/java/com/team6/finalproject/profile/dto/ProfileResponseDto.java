package com.team6.finalproject.profile.dto;

import com.team6.finalproject.profile.entity.Profile;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfileResponseDto {
    private String nickname;
    private String introduction;
    private String profileImage;
    private String locate;
    private Long userScore;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ProfileResponseDto(Profile profile) {
        this.nickname = profile.getNickname();
        this.introduction = profile.getIntroduction();
        this.profileImage = profile.getProfileImage();
        this.locate = profile.getLocate();
        this.userScore = profile.getUserScore();
        this.createdAt = profile.getCreatedAt();
        this.modifiedAt = profile.getModifiedAt();
    }
}
