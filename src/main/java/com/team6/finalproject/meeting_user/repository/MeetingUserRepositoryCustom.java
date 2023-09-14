package com.team6.finalproject.meeting_user.repository;

import com.team6.finalproject.meeting_user.entity.MeetingUser;

import java.util.List;
import java.util.Optional;

public interface MeetingUserRepositoryCustom {
    List<MeetingUser> findByMeetingUsers(Long meetingId);
    public Optional<MeetingUser> findByActiveMeetingAndUser(Long meetingId, Long userId);
}
