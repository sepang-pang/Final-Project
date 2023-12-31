package com.team6.finalproject.meeting.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.member.repository.MemberRepository;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.meeting.dto.MeetingPlaceRequestDto;
import com.team6.finalproject.meeting.dto.MeetingRequestDto;
import com.team6.finalproject.meeting.dto.MeetingResponseDto;
import com.team6.finalproject.meeting.dto.MeetingScheduleRequestDto;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting.repository.MeetingRepository;
import com.team6.finalproject.meeting_user.entity.MeetingUser;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@Slf4j(topic = "MeetingServiceImpl")
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ClubService clubService;
    private final MemberRepository memberRepository;
    private final FileUploader fileUploader;


    @Override
    @Transactional // 모임 생성
    public void createPost(Long clubId, MeetingRequestDto meetingRequestDto, MultipartFile file, User user) throws NotExistResourceException, IOException {

        Club club = clubService.findClub(clubId);

        String media = fileUploader.upload(file);

        log.info("1");
        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(user.getId(), club.getId()).isEmpty()) {
            throw new IllegalArgumentException("해당 동호회에 가입되어 있지 않습니다.");
        }

        log.info("2");

        // meetingRequestDto 필드값 중 하나라도 null 값이면 예외 발생
        if (meetingRequestDto.getTitle() == null || meetingRequestDto.getDescription() == null || meetingRequestDto.getMaxMember() == 0 || meetingRequestDto.getDate() == null || meetingRequestDto.getPlace() == null) {
            throw new IllegalArgumentException("필수 입력값이 누락되었습니다.");
        }

        // 모임 생성
        Meeting meeting = new Meeting(meetingRequestDto, media, club, user);

        log.info("3");
        // 모임 생성자를 모임 참여자로 추가
        MeetingUser meetingUser = new MeetingUser(meeting, user);

        log.info("4");
        // 모임 참여자 추가
        meeting.addMeetingUser(meetingUser);

        log.info("5");
        // 데이터베이스에 저장
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional // 모임 완료
    public ResponseEntity<ApiResponseDto> completedMeeting(Long meetingId, User user) {
        Meeting meeting = findMeeting(meetingId);

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }


        // 이미 완료된 모임인지 확인
        if (meeting.getIsCompleted()) {
            throw new IllegalArgumentException("이미 완료된 모임입니다.");
        }

        // 모임 완료 설정
        meeting.completed();

        // 활동 점수 증가
        meeting.getClub().updateActivityScore(20);

        return ResponseEntity.ok().body(new ApiResponseDto("모임이 완료되었습니다.", 200));
    }

    @Override
    @Transactional(readOnly = true) // 모임 조회
    public MeetingResponseDto getMeeting(Long meetingId, User user) throws NotExistResourceException {

        Meeting meeting = meetingRepository.findByMeeting(meetingId)
                .orElseThrow(() -> new NotExistResourceException("존재하지 않는 모임입니다."));

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(user.getId(), meeting.getClub().getId()).isEmpty()) {
            throw new IllegalArgumentException("해당 동호회에 가입되어 있지 않습니다.");
        }

        int userCount = meetingRepository.countByMeetingUser(meetingId).size();

        Long commentCount = meeting.getMeetingComments().stream()
                .filter(comment -> !comment.isDeleted())
                .count();

        return new MeetingResponseDto(findMeeting(meetingId), userCount, commentCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponseDto> getCompletedMeeting(Long clubId) {
        List<Meeting> meetings = meetingRepository.findByCompletedMeeting(clubId);

        // 존재 하지 않을시 예외 발생
        if (meetings.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 모임입니다.");
        }


        return meetings.stream()
                .map(meeting -> {
                    int userCount = meetingRepository.countByMeetingUser(meeting.getId()).size();
                    Long commentCount = meeting.getMeetingComments().stream()
                            .filter(comment -> !comment.isDeleted())
                            .count();
                    return new MeetingResponseDto(meeting, userCount, commentCount); // MeetingResponseDto의 생성자를 이와 같이 수정해야 합니다.
                })
                .toList();



    }

    @Override
    @Transactional(readOnly = true) // 미완료된 모임 조회
    public List<MeetingResponseDto> getUncompletedMeeting(Long clubId) {
        List<Meeting> meetings = meetingRepository.findByUncompletedMeeting(clubId);

        return meetings.stream()
                .map(meeting -> {
                    int userCount = meetingRepository.countByMeetingUser(meeting.getId()).size();
                    Long commentCount = meeting.getMeetingComments().stream()
                            .filter(comment -> !comment.isDeleted())
                            .count();
                    return new MeetingResponseDto(meeting, userCount, commentCount); // MeetingResponseDto의 생성자를 이와 같이 수정해야 합니다.
                })
                .toList();
    }

    @Override
    @Transactional // 모임 수정
    public void updateMeeting(Long meetingId, MeetingRequestDto meetingRequestDto, MultipartFile file, User user) throws IOException {

        log.info("updateMeeting 1");

        Meeting meeting = findMeeting(meetingId);

        log.info("updateMeeting 2");

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(user.getId(), meeting.getClub().getId()).isEmpty()) {
            throw new IllegalArgumentException("해당 동호회에 가입되어 있지 않습니다.");
        }

        log.info("updateMeeting 3");

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }

        log.info("updateMeeting 4");


        String media;

        // 파일 검사 및 처리
        if (file == null || file.isEmpty()) {
            media = meeting.getMedia();
        } else {
            // 수정 시 기존 객체 버킷에서 삭제
            if (meeting.getMedia() != null) {
                fileUploader.deleteFile(meeting.getMedia());
            }
            media = fileUploader.upload(file);
        }

        log.info("updateMeeting 6");

        meeting.update(meetingRequestDto, media);

        log.info("updateMeeting 7");
    }

    @Override
    @Transactional // 모임 삭제
    public void deleteMeeting(Long meetingId, User user) {
        Meeting meeting = findMeeting(meetingId);
        log.info("deleteMeeting 1");

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }

        log.info("deleteMeeting 3");

        meeting.deleted();
    }

    @Override
    @Transactional // 모임 일정 수정
    public void updateSchedule(Long postId, MeetingScheduleRequestDto meetingScheduleRequestDto, User user) {
        Meeting meeting = findMeeting(postId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.getClub().getId(), user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        meeting.updateSchedule(meetingScheduleRequestDto);
    }

    @Override
    @Transactional // 모임 장소 수정
    public void updatePlace(Long postId, MeetingPlaceRequestDto meetingplaceRequestDto, User user) {
        Meeting meeting = findMeeting(postId);

        // 작성자가 해당하는 동호회에 포함 돼 있는지 확인.
        if (memberRepository.findActiveUserAndClub(meeting.getClub().getId(), user.getId()).isEmpty()) {
            throw new RejectedExecutionException();
        }

        // 작성자만 수정 가능하게 예외처리.
        if (!meeting.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        meeting.updatePlace(meetingplaceRequestDto);
    }

    @Override // 모임 조회 메서드
    public Meeting findMeeting(Long meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}