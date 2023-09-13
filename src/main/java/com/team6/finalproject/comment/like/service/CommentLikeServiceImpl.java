package com.team6.finalproject.comment.like.service;

import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.comment.like.entity.CommentLike;
import com.team6.finalproject.comment.like.repository.CommentLikeRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;


    // 댓글 좋아요 여부
    @Override
    @Transactional(readOnly = true)
    public Boolean commentLikeCheck(Long commentId, User user) {
        return commentLikeRepository.existsByActiveCommentIdAndUser(commentId, user.getId()).isPresent(); // 좋아요 여부 = true 면 좋아요 누른거, false 면 좋아요 안누른거
    }

    // 댓글 좋아요
    @Override
    @Transactional(readOnly = true)
    public Optional<CommentLike> findCommentLike(Long commentId) {
        return commentLikeRepository.findByActiveCommentId(commentId);
    }


    // 댓글 좋아요 수
    @Override
    @Transactional(readOnly = true)
    public int countCommentLike(Long commentId) {
        return commentLikeRepository.countByActiveCommentId(commentId).size();
    }

    // 댓글 저장
    @Override
    public void save(CommentLike like) {
        commentLikeRepository.save(like);
    }

    public void checkedUser (CommentLike commentLike, User user) throws NotOwnedByUserException {
        if (!commentLike.getUser().getId().equals(user.getId())) {
            throw new NotOwnedByUserException("본인이 누른 좋아요가 아닙니다.");
        }
    }




}
