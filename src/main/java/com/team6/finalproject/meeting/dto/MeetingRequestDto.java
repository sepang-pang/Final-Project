package com.team6.finalproject.meeting.dto;


import com.team6.finalproject.club.enums.ActivityTypeEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MeetingRequestDto {
    private String name;
    private String description;
    private int maxMember;
    private ActivityTypeEnum ACTIVITY_TYPE;
    private LocalDateTime date;
    private String place;
}
