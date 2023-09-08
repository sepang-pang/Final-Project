package com.team6.finalproject.meeting.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.meeting.entity.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.meeting.entity.QMeeting.meeting;

@Repository
@RequiredArgsConstructor
public class MeetingRepositoryCustomImpl implements MeetingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Meeting> findByMeeting(Long meetingId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(meeting)
                                .where(meeting.id.eq(meetingId)
                                        .and(meeting.isDeleted.eq(false))) // 삭제되지 않은 미팅만 조회
                                .fetchOne()
                );
    }

    // 완료된 모임 조회
    @Override
    public List<Meeting> findByCompletedMeeting(Long clubId) {
        return
                jpaQueryFactory
                        .selectFrom(meeting)
                        .where(meeting.club.id.eq(clubId)
                                .and(meeting.isCompleted.eq(true))
                                .and(meeting.isDeleted.eq(false))) // 삭제되지 않은 미팅만 조회
                        .fetch();
    }

    // 미완료된 모임 조회
    @Override
    public List<Meeting> findByUncompletedMeeting(Long clubId) {
        return
                jpaQueryFactory
                        .selectFrom(meeting)
                        .where(meeting.club.id.eq(clubId)
                                .and(meeting.isCompleted.eq(false))
                                .and(meeting.isDeleted.eq(false))) // 삭제되지 않은 미팅만 조회
                        .fetch();
    }
}
