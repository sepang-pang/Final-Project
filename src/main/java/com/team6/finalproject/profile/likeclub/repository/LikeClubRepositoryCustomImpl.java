package com.team6.finalproject.profile.likeclub.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.likeclub.entity.LikeClub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.team6.finalproject.profile.likeclub.entity.QLikeClub.likeClub;

@Repository
@RequiredArgsConstructor
public class LikeClubRepositoryCustomImpl implements LikeClubRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public LikeClub findByClubAndProfile(Club club, Profile profile) {
        return jpaQueryFactory
                .selectFrom(likeClub)
                .where(likeClub.club.eq(club),
                        likeClub.profile.eq(profile),
                        likeClub.isDeleted.eq(false))
                .fetchOne();
    }
}
