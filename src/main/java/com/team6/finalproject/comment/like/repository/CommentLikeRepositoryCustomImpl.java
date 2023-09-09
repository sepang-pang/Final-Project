package com.team6.finalproject.comment.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.comment.like.entity.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.comment.like.entity.QCommentLike.commentLike;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryCustomImpl implements CommentLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CommentLike> findByActiveCommentId(Long commentId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(commentLike)
                                .where(commentLike.comment.commentId.eq(commentId)
                                        .and(commentLike.isDeleted.eq(false)))
                                .fetchOne()
                );
    }

}
