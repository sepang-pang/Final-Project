package com.team6.finalproject.comment.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotLikedYetException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.advice.custom.SelfLikeNotAllowedException;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.dto.CommentResponseDto;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.comment.like.entity.CommentLike;
import com.team6.finalproject.comment.like.service.CommentLikeService;
import com.team6.finalproject.comment.repository.CommentRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.meeting.service.MeetingService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CommentServiceImpl")
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MeetingService meetingService;
    private final MemberService memberService;
    private final CommentLikeService commentLikeService;

    // 댓글 작성
    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) throws NotExistResourceException {
        Meeting meeting = meetingService.findMeeting(commentRequestDto.getMeetingId());

        System.out.println("가입여부" + memberService.existJoinClub(user.getId(), meeting.getClub().getId()));

        if(!memberService.existJoinClub(user.getId(), meeting.getClub().getId())) {
            throw new NotExistResourceException("동호회에 가입하지 않았습니다.");
        }

        Comment comment = new Comment(commentRequestDto, user, meeting);

        commentRepository.save(comment);

        log.info("5");
        return new CommentResponseDto(comment);
    }

    // 모임 댓글 조회
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> readAllMeetingComment(Long meetingId) throws NotExistResourceException {
        List<Comment> comments = commentRepository.findByActiveMeetingId(meetingId);

        if (comments.isEmpty()) {
            throw new NotExistResourceException("댓글이 존재하지 않습니다.");
        }

        return comments.stream()
                .map(comment -> new CommentResponseDto(comment, commentLikeService.countCommentLike(comment.getCommentId())))
                .toList();
    }

    // 댓글 수정
    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) throws NotExistResourceException, NotOwnedByUserException {
        Comment comment = findComment(commentId);
        log.info("댓글 수정");

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

    // 댓글 좋아요
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> commentLike(Long commentId, User user) throws SelfLikeNotAllowedException, NotExistResourceException {
        Comment comment = findComment(commentId);

        CommentLike like = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

//        if (user.getId().equals(comment.getUser().getId())) {
//            throw new SelfLikeNotAllowedException("본인 댓글엔 좋아요를 할 수 없습니다.");
//        }

        commentLikeService.save(like);

        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 완료", 200));
    }

    // 댓글 좋아요 취소
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> CommentDislike(Long commentId, User user) throws NotLikedYetException, NotOwnedByUserException {

        CommentLike like = commentLikeService.findCommentLike(commentId).orElseThrow(
                () -> new NotLikedYetException("좋아요를 누르지 않은 댓글입니다.")
        );

        commentLikeService.checkedUser(like, user);

        like.dislike();

        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 취소 완료", 200));

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
