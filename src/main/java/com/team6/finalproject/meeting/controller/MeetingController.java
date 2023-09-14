package com.team6.finalproject.meeting.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.comment.like.service.CommentLikeService;
import com.team6.finalproject.comment.service.CommentService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.meeting.dto.MeetingPlaceRequestDto;
import com.team6.finalproject.meeting.dto.MeetingRequestDto;
import com.team6.finalproject.meeting.dto.MeetingResponseDto;
import com.team6.finalproject.meeting.dto.MeetingScheduleRequestDto;
import com.team6.finalproject.meeting.service.MeetingService;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/meeting-detail/{meetingId}")
    public String clubPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("meetingId") Long meetingId, Model model) {
        model.addAttribute("meetingId", meetingId);
        model.addAttribute("username", userDetails.getUsername()); // 현재 인가된 사용자의 이름
        model.addAttribute("meetingUsername", meetingService.findMeeting(meetingId).getUser().getUsername()); // 모임 작성자의 이름

        return "meeting-detail";
    }

    @GetMapping("/meeting-update/{meetingId}")
    public String updateMeeting(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("meetingId") Long meetingId, Model model) {
        model.addAttribute("meetingId", meetingId);
        model.addAttribute("username", userDetails.getUsername()); // 현재 인가된 사용자의 이름
        model.addAttribute("meetingUsername", meetingService.findMeeting(meetingId).getUser().getUsername()); // 모임 작성자의 이름

        return "meeting-update";
    }

    // 모임 생성.
    @PostMapping("/{clubId}")
    @ResponseBody
    public void createMeeting(@PathVariable Long clubId, @RequestBody MeetingRequestDto meetingRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, IOException {
        meetingService.createPost(clubId, meetingRequestDto, userDetails.getUser());
    }

    // 모임 완료
    @PatchMapping("/{meetingId}/completed")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> completedMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return meetingService.completedMeeting(meetingId, userDetails.getUser());
    }

    // 모임 조회.
    @GetMapping("/{meetingId}")
    @ResponseBody
    public MeetingResponseDto getMeeting(@PathVariable Long meetingId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return meetingService.getMeeting(meetingId,userDetails.getUser());
    }

    // 완료된 모임 조회
    @GetMapping("/{clubId}/completed")
    @ResponseBody
    public List<MeetingResponseDto> getCompletedMeeting(@PathVariable Long clubId) {
        return meetingService.getCompletedMeeting(clubId);
    }

    // 미완료된 모임 조회
    @GetMapping("/{clubId}/uncompleted")
    @ResponseBody
    public List<MeetingResponseDto> getUncompletedMeeting(@PathVariable Long clubId) {
        return meetingService.getUncompletedMeeting(clubId);
    }

    // 모임 전체 업데이트.
    @PutMapping("/{meetingId}")
    @ResponseBody
    public void updateMeeting(@PathVariable Long meetingId, @RequestPart MeetingRequestDto meetingRequestDto, @RequestPart MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        meetingService.updateMeeting(meetingId, meetingRequestDto, file,userDetails.getUser());
    }

    // 모임 삭제.
    @DeleteMapping("/{meetingId}")
    @ResponseBody
    public void deleteMeeting(@PathVariable Long meetingId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.deleteMeeting(meetingId,userDetails.getUser());
    }

    // 모임 일정 수정.
    @PutMapping("/{meetingId}/schedule")
    public void updateSchedule(@PathVariable Long meetingId, @RequestBody MeetingScheduleRequestDto meetingScheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.updateSchedule(meetingId,meetingScheduleRequestDto,userDetails.getUser());
    }

    // 모임 장소 수정
    @PutMapping("/{meetingId}/place")
    public void updatePlace(@PathVariable Long meetingId, @RequestBody MeetingPlaceRequestDto meetingplaceRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.updatePlace(meetingId,meetingplaceRequestDto,userDetails.getUser());
    }
}
