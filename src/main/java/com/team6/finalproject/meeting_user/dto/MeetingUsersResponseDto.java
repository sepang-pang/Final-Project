package com.team6.finalproject.meeting_user.dto;

import com.team6.finalproject.meeting_user.entity.MeetingUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MeetingUsersResponseDto {
    private String nickname;
    private String introduction;
    private String profileImage;

    public MeetingUsersResponseDto(MeetingUser meetingUser) {
        this.nickname = meetingUser.getUser().getProfile().getNickname();
        this.introduction = meetingUser.getUser().getProfile().getIntroduction();
        this.profileImage = meetingUser.getUser().getProfile().getProfileImage();
    }
}
