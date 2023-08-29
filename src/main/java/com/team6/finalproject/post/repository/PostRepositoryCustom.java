package com.team6.finalproject.post.repository;

import com.team6.finalproject.post.entity.Post;

import java.util.Optional;

public interface PostRepositoryCustom {
    public Optional<Post> findByActiveId(Long id);
}
