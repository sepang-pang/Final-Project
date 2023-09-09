package com.team6.finalproject.profile.dto;

import com.team6.finalproject.profile.entity.Profile;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfileResponseDto {
    private String nickname;
    private String introduction;
    private String profileImage;
    private String locate;
    private Long userScore;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> profileInterestNames;

    public ProfileResponseDto(Profile profile) {
        this.nickname = profile.getNickname();
        this.introduction = profile.getIntroduction();
        this.profileImage = profile.getProfileImage();
        this.locate = profile.getLocate();
        this.userScore = profile.getUserScore();
        this.createdAt = profile.getCreatedAt();
        this.modifiedAt = profile.getModifiedAt();
        this.profileInterestNames = profile.getProfileInterests().stream()
                .filter(profileInterest -> !profileInterest.isDeleted())
                .map(profileInterest -> profileInterest.getInterestMinor().getMinorName())
                .collect(Collectors.toList());
    }
}
