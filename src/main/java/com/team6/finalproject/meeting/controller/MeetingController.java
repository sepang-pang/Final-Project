package com.team6.finalproject.meeting.controller;

import com.team6.finalproject.meeting.dto.*;
import com.team6.finalproject.meeting.service.MeetingService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    // 모임 생성.
    @PostMapping("/{clubId}")
    public void createMeeting(@PathVariable Long clubId, @RequestBody MeetingRequestDto meetingRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.createPost(clubId, meetingRequestDto,userDetails.getUser());
    }

    // 모임 조회.
    @GetMapping("/{meetingId}")
    public MeetingResponseDto getMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return meetingService.getMeeting(meetingId,userDetails.getUser());
    }

    // 모임 전체 업데이트.
    @PutMapping("/{meetingId}")
    public void updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingRequestDto meetingRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.updateMeeting(meetingId, meetingRequestDto,userDetails.getUser());
    }

    // 모임 삭제.
    @DeleteMapping("/{meetingId}")
    public void deleteMeeting(@PathVariable Long meetingId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.deleteMeeting(meetingId,userDetails.getUser());
    }

    // 모임 일정 수정.
    @PutMapping("/{meetingId}/schedule")
    public void updateSchedule(@PathVariable Long meetingId,@RequestBody MeetingScheduleRequestDto meetingScheduleRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.updateSchedule(meetingId,meetingScheduleRequestDto,userDetails.getUser());
    }

    // 모임 장소 수정
    @PutMapping("/{meetingId}/place")
    public void updatePlace(@PathVariable Long meetingId,@RequestBody MeetingPlaceRequestDto meetingplaceRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.updatePlace(meetingId,meetingplaceRequestDto,userDetails.getUser());
    }
}
