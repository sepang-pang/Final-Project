package com.team6.finalproject.meeting_user.service;

import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting.service.MeetingService;
import com.team6.finalproject.meeting_user.dto.MeetingUsersResponseDto;
import com.team6.finalproject.meeting_user.entity.MeetingUser;
import com.team6.finalproject.meeting_user.repository.MeetingUserRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "MeetingUserService")
public class MeetingUserService {

    private final MeetingUserRepository meetingUserRepository;
    private final MeetingService meetingService;

    // 모임 참석 유저 조회.
    @Transactional(readOnly = true)
    public List<MeetingUsersResponseDto> getMeetingUsers(Long meetingId) {
        List<MeetingUser> meetingUsers = meetingUserRepository.findByMeetingUsers(meetingId);
        return meetingUsers.stream().map(MeetingUsersResponseDto::new).toList();
    }

    // 모임 참석.
    @Transactional
    public void meetingAttend(Long meetingId, User user) {
        log.info("참여 시도 1");
        Meeting meeting = meetingService.findMeeting(meetingId);

        log.info("참여 시도 2");
        // 참여자 수 조회
        int participants = meetingUserRepository.findByMeetingUsers(meetingId).size();

        log.info("참여 시도 3");
        // 정원에 도달 했을 경우 모임 참여 불가
        if (meeting.getMaxMember() < participants) {
            throw new IllegalArgumentException("참여자 수가 다 찼습니다.");
        }

        log.info("참여 시도 4");
        // 이미 마감된 모임이면 참여 불가
        if (meeting.getIsCompleted()) {
            throw new IllegalArgumentException("이미 마감된 모임입니다.");
        }

        log.info("참여 시도 5");
        MeetingUser meetingUser = MeetingUser.builder()
                .meeting(meeting)
                .user(user)
                .isDeleted(false)
                .build();

        log.info("참여 시도 6");
        meetingUserRepository.save(meetingUser);
    }

    // 모임 참석 취소.
    @Transactional
    public void meetingCancel(Long meetingId, User user) {
        log.info("참여취소 시도 1");
        MeetingUser meetingUser = meetingUserRepository.findByActiveMeetingAndUser(meetingId, user.getId())
                        .orElseThrow(()-> new IllegalArgumentException("모임에 참여하지 않은 유저입니다."));

        log.info("참여취소 시도 2");

        if (meetingUser.getMeeting().getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("모임장은 모임을 취소할 수 없습니다.");
        }

        log.info("참여취소 시도 3");
        meetingUser.IsDeleted();

        log.info("참여취소 시도 4");
    }

    // 모임 참석 여부 확인.
    public boolean isAttend(Long meetingId, User user) {
       return meetingUserRepository.findByActiveMeetingAndUser(meetingId, user.getId()).isPresent();
    }
}
