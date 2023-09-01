package com.team6.finalproject.meeting_user.service;

import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting.repository.MeetingRepository;
import com.team6.finalproject.meeting.service.MeetingService;
import com.team6.finalproject.meeting_user.dto.MeetingUsersResponseDto;
import com.team6.finalproject.meeting_user.entity.MeetingUser;
import com.team6.finalproject.meeting_user.repository.MeetingUserRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingUserService {

    private final MeetingUserRepository meetingUserRepository;
    private final MeetingService meetingService;

    // 모임 참석 유저 조회.
    public MeetingUsersResponseDto getMeetingUsers(Long meetingId) {

        List<MeetingUser> meetingUsers = meetingUserRepository.findByMeetingUsers(meetingId);
        return new MeetingUsersResponseDto(meetingUsers);
    }

    // 모임 참석.
    public void meetingAttend(Long meetingId, User user) {
        Meeting meeting = meetingService.findMeeting(meetingId);

        MeetingUser meetingUser = MeetingUser.builder()
                .meeting(meeting)
                .user(user)
                .build();

        meeting.addMetingUser(meetingUser);

        meetingUserRepository.save(meetingUser);
    }

    // 모임 참석 취소.
    public void meetingCancel(Long meetingUserId,User user) {
        MeetingUser meetingUser = meetingUserRepository.findById(meetingUserId).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저가 존재하지 않습니다."));

        meetingUser.IsDeleted();
    }
}
