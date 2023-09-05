package com.team6.finalproject.meeting.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MeetingScheduleRequestDto {
    private LocalDateTime schedule;
}
