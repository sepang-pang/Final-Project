package com.team6.finalproject.comment.like.service;

import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.comment.like.entity.CommentLike;
import com.team6.finalproject.user.entity.User;

import java.util.Optional;

public interface CommentLikeService {


    Boolean commentLikeCheck(Long commentId, User user);

    int countCommentLike(Long commentId);

    void save(CommentLike like);

    void checkedUser(CommentLike commentLike, User user) throws NotOwnedByUserException;

    Optional<CommentLike> findCommentLike(Long commentId);

}