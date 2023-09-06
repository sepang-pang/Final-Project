package com.team6.finalproject.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequestDto {
    private String name;
    private Long minorId;
    private String description;
    private boolean trialAvailable;
    private boolean isOnline;
    private boolean openJoinType;
    private int maxMember;
    private int minAge;
    private int maxAge;
}
