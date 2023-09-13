package com.team6.finalproject.club.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.club.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Member> findActiveUser(Long userId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(member)
                                .where(member.user.id.eq(userId)
                                        .and(member.isDeleted.eq(false)))
                                .fetchOne()
                );
    }

    @Override
    public Optional<Member> findActiveUserAndClub(Long userId, Long clubId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(member)
                                .where(member.user.id.eq(userId)
                                        .and(member.club.id.eq(clubId))
                                        .and(member.isDeleted.eq(false)))
                                .fetchOne()
                );
    }

    @Override
    public List<Member> findActiveMembers(Long clubId) {
        return
                jpaQueryFactory
                        .selectFrom(member)
                        .where(member.club.id.eq(clubId)
                                .and(member.isDeleted.eq(false)))
                        .fetch();
    }
}
