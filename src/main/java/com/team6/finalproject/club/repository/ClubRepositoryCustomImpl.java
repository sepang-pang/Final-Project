package com.team6.finalproject.club.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.entity.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.club.entity.QClub.club;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Club> findByActiveId(Long id) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(club)
                                .where(club.id.eq(id)
                                        .and(club.isDeleted.eq(false))) // 삭제되지 않은 동호회만 조회
                                .fetchOne()
                );
    }

    @Override
    public Optional<Club> findByActiveClubName(String name) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(club)
                                .where(club.name.eq(name)
                                        .and(club.isDeleted.eq(false))) // 삭제되지 않은 동호회만 조회
                                .fetchOne()
                );
    }

    @Override
    public List<Club> findByActiveInterestMajor(Long majorId) {
        return
                jpaQueryFactory
                        .selectFrom(club)
                        .where(club.minor.interestMajor.id.eq(majorId)
                                .and(club.isDeleted.eq(false)))
                        .orderBy(club.createdAt.desc()) // 최신순 정렬
                        .fetch();
    }

    @Override // 동호회 - 소주제 별로 정렬
    public List<Club> findByActiveInterestMinor(Long minorId) {
        return
                jpaQueryFactory
                        .selectFrom(club)
                        .where(club.minor.interestMajor.id.eq(minorId)
                                .and(club.isDeleted.eq(false)))
                        .orderBy(club.createdAt.desc()) // 최신순 정렬
                        .fetch();
    }

    @Override
    public List<Club> findClubsByUserAge(int userAge) {
        Predicate predicate = club.minAge.loe(userAge)
                .and(club.maxAge.goe(userAge))
                .and(club.isDeleted.eq(false));
        return
                jpaQueryFactory
                        .selectFrom(club)
                        .where(predicate)
                        .fetch();
    }
}

