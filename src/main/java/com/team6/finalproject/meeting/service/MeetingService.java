package com.team6.finalproject.meeting.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.member.repository.MemberRepository;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.meeting.dto.*;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting.repository.MeetingRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ClubService clubService;
    private final MemberRepository memberRepository;

    // 모임 생성.
    @Transactional
    public void createPost(Long clubId, MeetingRequestDto meetingRequestDto, User user) throws NotExistResourceException {

        Club club = clubService.findClub(clubId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(club.getId(),user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        Meeting meeting = Meeting.builder()
                .name(meetingRequestDto.getName())
                .description(meetingRequestDto.getDescription())
                .maxMember(meetingRequestDto.getMaxMember())
                .ACTIVITY_TYPE(meetingRequestDto.getACTIVITY_TYPE())
                .date(meetingRequestDto.getDate())
                .place(meetingRequestDto.getPlace())
                .isCompleted(false)
                .club(club)
                .user(user)
                .build();

        club.addMeeting(meeting);

        meetingRepository.save(meeting);
    }

    // 모임 조회.
    @Transactional(readOnly = true)
    public MeetingResponseDto getMeeting(Long meetingId, User user) {

        Optional<Meeting> meeting = meetingRepository.findByMeeting(meetingId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.get().getClub().getId(),user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        return new MeetingResponseDto(findMeeting(meetingId));
    }

    // 모임 전체 업데이트.
    @Transactional
    public void updateMeeting(Long meetingId, MeetingRequestDto meetingRequestDto, User user) {
        Meeting meeting = findMeeting(meetingId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.getClub().getId(),user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        meeting.update(meetingRequestDto);
    }

    // 모임 삭제.
    @Transactional
    public void deleteMeeting(Long meetingId,User user) {
        Meeting meeting = findMeeting(meetingId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.getClub().getId(),user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        meeting.deleted();
    }

    // 모임 일정 업데이트.
    @Transactional
    public void updateSchedule(Long postId, MeetingScheduleRequestDto meetingScheduleRequestDto,User user) {
        Meeting meeting = findMeeting(postId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.getClub().getId(),user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        meeting.updateSchedule(meetingScheduleRequestDto);
    }

    // 모임 장소 업데이트.
    @Transactional
    public void updatePlace(Long postId, MeetingPlaceRequestDto meetingplaceRequestDto,User user) {
        Meeting meeting = findMeeting(postId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.getClub().getId(),user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        meeting.updatePlace(meetingplaceRequestDto);
    }

    /////////////////////////////////////////////조회 메서드/////////////////////////////////////////

    // 미팅 조회 메서드
    @Transactional
    public Meeting findMeeting(Long meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
