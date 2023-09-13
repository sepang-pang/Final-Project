package com.team6.finalproject.meeting.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.meeting.dto.MeetingPlaceRequestDto;
import com.team6.finalproject.meeting.dto.MeetingRequestDto;
import com.team6.finalproject.meeting.dto.MeetingResponseDto;
import com.team6.finalproject.meeting.dto.MeetingScheduleRequestDto;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MeetingService {

    // 모임 생성.
    public void createPost(Long clubId, MeetingRequestDto meetingRequestDto, User user) throws NotExistResourceException, IOException;

    // 모임 완료
    public ResponseEntity<ApiResponseDto> completedMeeting(Long meetingId, User user);

    // 모임 조회
    public MeetingResponseDto getMeeting(Long meetingId, User user) throws NotExistResourceException;

    // 완료된 모임 조회
    public List<MeetingResponseDto> getCompletedMeeting(Long clubId);

    // 미완료된 모임 조회
    public List<MeetingResponseDto> getUncompletedMeeting(Long clubId);

    // 모임 전체 업데이트.
    public void updateMeeting(Long meetingId, MeetingRequestDto meetingRequestDto, MultipartFile file, User user) throws IOException;

    // 모임 삭제
    public void deleteMeeting(Long meetingId, User user);

    // 모임 일정 업데이트
    public void updateSchedule(Long postId, MeetingScheduleRequestDto meetingScheduleRequestDto, User user);

    // 모임 장소 업데이트
    public void updatePlace(Long postId, MeetingPlaceRequestDto meetingplaceRequestDto, User user);


    /////////////////////////////////////////////조회 메서드/////////////////////////////////////////

    // 미팅 조회 메서드
    public Meeting findMeeting(Long meetingId);

}
