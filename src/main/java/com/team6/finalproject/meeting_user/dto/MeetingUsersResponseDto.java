package com.team6.finalproject.meeting_user.dto;

import com.team6.finalproject.meeting_user.entity.MeetingUser;
import lombok.Getter;

import java.util.List;

@Getter
public class MeetingUsersResponseDto {
    private List<String> usernames;

    public MeetingUsersResponseDto(List<MeetingUser> meetingUsers) {
        for (MeetingUser meetingUser : meetingUsers) {
            this.usernames.add(meetingUser.getUser().getUsername());
        }
    }
}
