package com.team6.finalproject.meeting_user.repository;

import com.team6.finalproject.meeting_user.entity.MeetingUser;

import java.util.List;

public interface MeetingUserRepositoryCustom {
    List<MeetingUser> findByMeetingUsers(Long meetingId);
}
