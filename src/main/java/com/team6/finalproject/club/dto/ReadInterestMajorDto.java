package com.team6.finalproject.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadInterestMajorDto {
    private String nickName;
    private String name;
    private String description;
    private boolean trialAvailable;
    private String activityType;
    private String joinType;
    private int maxMember;
}
