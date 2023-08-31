package com.team6.finalproject.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.comment.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Comment> findByActiveId(Long id) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(comment)
                                .where(comment.commentId.eq(id)
                                        .and(comment.isDeleted.eq(false)))
                                .fetchOne()

                );
    }
}