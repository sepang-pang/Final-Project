package com.team6.finalproject.meeting_user.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.meeting_user.entity.MeetingUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.meeting_user.entity.QMeetingUser.meetingUser;

@Repository
@RequiredArgsConstructor
public class MeetingUserRepositoryCustomImpl implements MeetingUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MeetingUser> findByMeetingUsers(Long meetingId) {
        return
                jpaQueryFactory
                        .selectFrom(meetingUser)
                        .where(meetingUser.meeting.id.eq(meetingId)
                                .and(meetingUser.isDeleted.eq(false))) // 삭제되지 않은 모임만 조회
                        .fetch();
    }

    @Override
    public Optional<MeetingUser> findByActiveMeetingAndUser(Long meetingId, Long userId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(meetingUser)
                                .where(meetingUser.meeting.id.eq(meetingId)
                                        .and(meetingUser.user.id.eq(userId))
                                        .and(meetingUser.isDeleted.eq(false)))
                                .fetchOne()
                );
    }
}
