package com.team6.finalproject.meeting.repository;

import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting_user.entity.MeetingUser;

import java.util.List;
import java.util.Optional;

public interface MeetingRepositoryCustom {
    Optional<Meeting> findByMeeting(Long meetingId); // 모임 상세 조회
    List<Meeting> findByCompletedMeeting(Long clubId); // 완료된 모임 조회
    List<Meeting> findByUncompletedMeeting(Long clubId); // 미완료된 모임 조회
    public List<MeetingUser> countByMeetingUser(Long meetingId); // 모임 참여 인원 수 조회
}
