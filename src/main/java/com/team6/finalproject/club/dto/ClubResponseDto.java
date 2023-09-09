package com.team6.finalproject.club.dto;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.profile.dto.ProfileNickNameDto;
import com.team6.finalproject.user.entity.User;
import lombok.Getter;

@Getter
public class ClubResponseDto {
    private String username;
    private String nickName;
    private String name;
    private String description;
    private String media;
    private boolean trialAvailable;
    private String activityType;
    private String joinType;
    private String locate;
    private int maxMember;
    private String major;
    private String minor;
    private int minAge;
    private int maxAge;



    public ClubResponseDto(User user, Club club, InterestMajorDto interestMajorDto, InterestMinorDto interestMinorDto) {
        this.username = user.getUsername();
        this.nickName = club.getNickName();
        this.name = club.getName();
        this.description = club.getDescription();
        this.media = club.getMedia();
        this.trialAvailable = club.isTrialAvailable();
        this.activityType = club.getActivityType().getActivity();
        this.joinType = club.getJoinType().getJoin();
        this.locate = club.getLocate();
        this.maxMember = club.getMaxMember();
        this.minAge = club.getMinAge();
        this.maxAge = club.getMaxAge();
        this.major = interestMajorDto.getName();
        this.minor = interestMinorDto.getName();
    }

    public ClubResponseDto(Club club, InterestMajorDto interestMajorDto, InterestMinorDto interestMinorDto) {
        this.username = club.getUsername();
        this.nickName = club.getNickName();
        this.name = club.getName();
        this.description = club.getDescription();
        this.media = club.getMedia();
        this.trialAvailable = club.isTrialAvailable();
        this.activityType = club.getActivityType().getActivity();
        this.joinType = club.getJoinType().getJoin();
        this.locate = club.getLocate();
        this.maxMember = club.getMaxMember();
        this.minAge = club.getMinAge();
        this.maxAge = club.getMaxAge();
        this.major = interestMajorDto.getName();
        this.minor = interestMinorDto.getName();
    }
}
