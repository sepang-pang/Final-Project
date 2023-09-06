package com.team6.finalproject.meeting.repository;

import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting_user.entity.MeetingUser;

import java.util.List;
import java.util.Optional;

public interface MeetingRepositoryCustom {
    Optional<Meeting> findByMeeting(Long meetingId);
}
