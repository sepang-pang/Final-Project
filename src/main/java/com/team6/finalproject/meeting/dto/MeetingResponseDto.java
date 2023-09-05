package com.team6.finalproject.meeting.dto;

import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.meeting.entity.Meeting;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MeetingResponseDto {

    private String name;
    private String description;
    private int maxMember;
    private ActivityTypeEnum ACTIVITY_TYPE;
    private LocalDateTime date;
    private String place;
    private Boolean isCompleted;
    private Boolean isDeleted;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public MeetingResponseDto(Meeting meeting) {
        this.name = meeting.getName();
        this.description = meeting.getDescription();
        this.maxMember = meeting.getMaxMember();
        this.ACTIVITY_TYPE = meeting.getACTIVITY_TYPE();
        this.date = meeting.getDate();
        this.place = meeting.getPlace();
        this.isCompleted = meeting.getIsCompleted();
        this.isDeleted = meeting.getIsDeleted();
        this.createAt = meeting.getCreatedAt();
        this.modifiedAt = meeting.getModifiedAt();
    }

}
