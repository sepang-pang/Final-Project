package com.team6.finalproject.post.postLike.repository;

import com.team6.finalproject.post.postLike.entity.PostLike;

import java.util.Optional;

public interface PostLikeRepositoryCustom {
    public Optional<PostLike> findByActivePostId(Long id);

}
