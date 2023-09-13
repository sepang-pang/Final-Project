package com.team6.finalproject.comment.like.repository;

import com.team6.finalproject.comment.like.entity.CommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepositoryCustom {

    public Optional<CommentLike> findByActiveCommentId(Long commentId);

    public Optional<CommentLike> existsByActiveCommentIdAndUser(Long commentId, Long id);

    public List<CommentLike> countByActiveCommentId(Long commentId);
}
