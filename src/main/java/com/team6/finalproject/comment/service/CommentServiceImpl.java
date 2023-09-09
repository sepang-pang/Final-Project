package com.team6.finalproject.comment.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.dto.CommentResponseDto;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.comment.repository.CommentRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting.service.MeetingService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MeetingService meetingService;
    private final MemberService memberService;

    // 댓글 작성
    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) throws NotExistResourceException {

        Meeting meeting = meetingService.findMeeting(commentRequestDto.getMeetingId());

        if(!memberService.existJoinClub(user.getId(), meeting.getClub().getId())) {
            throw new NotExistResourceException("동호회에 가입하지 않았습니다.");
        }

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .meeting(meeting)
                .nickname(user.getProfile().getNickname())
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 모임 댓글 조회
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> readAllMeetingComment(Long meetingId) throws NotExistResourceException {
        List<Comment> comments = commentRepository.findByActiveMeetingId(meetingId);

        if(comments.isEmpty()) {
            throw new NotExistResourceException("댓글이 존재하지 않습니다.");
        }

        return comments.stream().map(CommentResponseDto::new).toList();
    }

    // 댓글 수정
    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) throws NotExistResourceException, NotOwnedByUserException {
        Comment comment = findComment(commentId);

        checkedAuthor(comment, user);

        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteComment(Long commentId, User user) throws NotExistResourceException, NotOwnedByUserException {
        Comment comment = findComment(commentId);

        checkedAuthor(comment, user);

        comment.deleteComment();
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 완료", 200));
    }

    // 댓글 존재 여부 확인
    @Override
    @Transactional(readOnly = true)
    public Comment findComment(Long commentId) throws NotExistResourceException {
        return commentRepository.findByActiveId(commentId).orElseThrow(() ->
                new NotExistResourceException("댓글이 존재하지 않습니다.")
        );
    }

    // 본인 댓글인지 확인
    public void checkedAuthor(Comment comment, User user) throws NotOwnedByUserException {
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new NotOwnedByUserException("본인이 작성한 댓글이 아닙니다.");
        }
    }
}
