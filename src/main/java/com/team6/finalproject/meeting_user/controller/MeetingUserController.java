package com.team6.finalproject.meeting_user.controller;

import com.team6.finalproject.meeting_user.dto.MeetingUsersResponseDto;
import com.team6.finalproject.meeting_user.service.MeetingUserService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingUserController {

    private final MeetingUserService meetingUserService;

    // 모임 참석 유저 조회.
    @GetMapping("/{meetingId}/members")
    @ResponseBody
    public List<MeetingUsersResponseDto> getMeetingUsers(@PathVariable Long meetingId) {
        return meetingUserService.getMeetingUsers(meetingId);
    }

    // 모임 참석.
    @PutMapping("/{meetingId}/attend")
    @ResponseBody
    public void meetingAttend(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingUserService.meetingAttend(meetingId,userDetails.getUser());
    }

    // 모임 참석 취소.
    @DeleteMapping("/{meetingId}/cancel")
    @ResponseBody
    public void meetingCancel(@PathVariable Long meetingId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingUserService.meetingCancel(meetingId, userDetails.getUser());
    }

    // 모임 참석 여부 확인.
    @GetMapping("/{meetingId}/isAttend")
    @ResponseBody
    public boolean isAttend(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return meetingUserService.isAttend(meetingId, userDetails.getUser());
    }
}
