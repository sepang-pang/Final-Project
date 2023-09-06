package com.team6.finalproject.comment.commentLike.repository;

import com.team6.finalproject.comment.commentLike.entity.CommentLike;

import java.util.Optional;

public interface CommentLikeRepositoryCustom {

    public Optional<CommentLike> findByActiveCommentId(Long commentId);

}
