package com.team6.finalproject.club.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.club.member.entity.QMember;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.club.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Member> findActiveUserAndClub(User user, Club club){
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(member)
                                .where(member.user.eq(user)
                                        .and(member.club.eq(club))
                                        .and(member.user.isDeleted.eq(false))
                                        .and(member.club.isDeleted.eq(false)))
                                .fetchOne()
                );
    }
}
