package com.team6.finalproject.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Post> findByActiveId(Long id) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(post)
                                .where(post.postId.eq(id)
                                        .and(post.isDeleted.eq(false)))
                                .fetchOne()

                );
    }
}
