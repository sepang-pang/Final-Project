package com.team6.finalproject.comment.commentLike.service;

import com.team6.finalproject.comment.commentLike.entity.CommentLike;
import com.team6.finalproject.comment.commentLike.repository.CommentLikeRepository;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.comment.service.CommentService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    // 댓글 좋아요
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> commentLike(Long commentId, User user) {
        Comment comment = commentService.findComment(commentId);

        CommentLike like = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        if (user.getId().equals(comment.getUser().getId())) {
            throw new IllegalArgumentException("본인 댓글엔 좋아요를 할 수 없습니다.");
        }

        commentLikeRepository.save(like);

        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 완료", 200));
    }

    // 댓글 좋아요 취소
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> CommentDislike(Long commentId, User user) {

        CommentLike like = commentLikeRepository.findByActiveCommentId(commentId).orElseThrow(()->
                new IllegalArgumentException("좋아요를 누르지 않았습니다"));

        checkedUser(like, user);

        like.dislike();
        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 취소 완료", 200));

    }

    private void checkedUser (CommentLike commentLike, User user) {
        if (!commentLike.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 누른 좋아요가 아닙니다.");
        }
    }




}
