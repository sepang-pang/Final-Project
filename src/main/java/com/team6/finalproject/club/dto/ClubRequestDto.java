package com.team6.finalproject.club.dto;

import lombok.Getter;

@Getter
public class ClubRequestDto {
    private String name;
    private Long minorId;
    private String description;
    private boolean trialAvailable;
    private boolean isOnline;
    private boolean openJoinType;
    private int maxMember;
}
