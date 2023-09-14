package com.team6.finalproject.meeting_user.dto;

import com.team6.finalproject.meeting_user.entity.MeetingUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MeetingUsersResponseDto {
    private Long userId;
    private String username;
    private String nickname;
    private String introduction;
    private String profileImage;

    public MeetingUsersResponseDto(MeetingUser meetingUser) {
        this.userId = meetingUser.getUser().getId();
        this.username = meetingUser.getUser().getUsername();
        this.nickname = meetingUser.getUser().getProfile().getNickname();
        this.introduction = meetingUser.getUser().getProfile().getIntroduction();
        this.profileImage = meetingUser.getUser().getProfile().getProfileImage();
    }
}
