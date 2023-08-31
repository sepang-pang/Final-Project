package com.team6.finalproject.comment.repository;

import com.team6.finalproject.comment.entity.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {
    public Optional<Comment> findByActiveId(Long id);
}
