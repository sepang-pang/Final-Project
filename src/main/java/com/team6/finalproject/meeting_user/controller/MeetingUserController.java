package com.team6.finalproject.meeting_user.controller;

import com.team6.finalproject.meeting_user.dto.MeetingUsersResponseDto;
import com.team6.finalproject.meeting_user.service.MeetingUserService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingUserController {

    private final MeetingUserService meetingUserService;

    // 모임 참석 유저 조회.
    @GetMapping("/{meetingId}/users")
    public MeetingUsersResponseDto getMeetingUser(@PathVariable Long meetingId) {
        return meetingUserService.getMeetingUsers(meetingId);
    }

    // 모임 참석.
    @GetMapping("/{meetingId}/attend")
    public void meetingAttend(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingUserService.meetingAttend(meetingId,userDetails.getUser());
    }

    // 모임 참석 취소.
    @DeleteMapping("/{meetingUserId}/cancel")
    public void meetingCancel(@PathVariable Long meetingUserId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingUserService.meetingCancel(meetingUserId,userDetails.getUser());
    }
}
