package com.team6.finalproject.comment.like.repository;

import com.team6.finalproject.comment.like.entity.CommentLike;

import java.util.Optional;

public interface CommentLikeRepositoryCustom {

    public Optional<CommentLike> findByActiveCommentId(Long commentId);

}
