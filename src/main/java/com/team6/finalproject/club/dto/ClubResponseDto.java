package com.team6.finalproject.club.dto;

import com.team6.finalproject.club.entity.Club;
import lombok.Getter;

@Getter
public class ClubResponseDto {
    private String name;
    private String description;
    private boolean trialAvailable;
    private String activityType;
    private String joinType;
    private int maxMember;
    private String major;
    private String minor;


    public ClubResponseDto(Club club, InterestMajorDto interestMajorDto, InterestMinorDto interestMinorDto) {
        this.name = club.getName();
        this.description = club.getDescription();
        this.trialAvailable = club.isTrialAvailable();
        this.activityType = club.getActivityType().getActivity();
        this.joinType = club.getJoinType().getJoin();
        this.maxMember = club.getMaxMember();
        this.major = interestMajorDto.getName();
        this.minor = interestMinorDto.getName();
    }
}
