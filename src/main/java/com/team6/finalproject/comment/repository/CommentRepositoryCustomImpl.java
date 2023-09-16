package com.team6.finalproject.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<Comment> findByActiveMeetingId(Long meetingId) {
        return // order by commentId created_at desc
                jpaQueryFactory
                        .selectFrom(comment)
                        .where(comment.meeting.id.eq(meetingId)
                                .and(comment.isDeleted.eq(false)))
                        .orderBy(comment.createdAt.desc())
                        .fetch();
    }

}