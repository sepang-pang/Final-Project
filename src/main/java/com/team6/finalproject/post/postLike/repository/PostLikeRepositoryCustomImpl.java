package com.team6.finalproject.post.postLike.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.post.postLike.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.post.postLike.entity.QPostLike.postLike;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<PostLike> findByActivePostId(Long postId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(postLike)
                                .where(postLike.post.postId.eq(postId)
                                        .and(postLike.isDeleted.eq(false)))
                                .fetchOne()
                );
    }

}
