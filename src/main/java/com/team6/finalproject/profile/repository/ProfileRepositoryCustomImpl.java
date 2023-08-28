package com.team6.finalproject.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.profile.entity.QProfile.profile;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryCustomImpl implements ProfileRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Profile> existValidLocate(Long id) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(profile)
                                .where(profile.user.id.eq(id)
                                        .and(profile.locate.isNotNull())
                                        .and(profile.user.isDeleted.eq(false)))
                                .fetchOne()
                );
    }

    @Override
    public Optional<Profile> existValidInterest(Long id) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(profile)
                                .where(profile.user.id.eq(id)
                                        .and(profile.profileInterests.isNotEmpty())
                                        .and(profile.user.isDeleted.eq(false)))
                                .fetchOne()
                );
    }
}
