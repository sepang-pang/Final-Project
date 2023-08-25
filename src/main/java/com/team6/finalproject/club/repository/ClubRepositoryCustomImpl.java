package com.team6.finalproject.club.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.entity.QClub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.club.entity.QClub.club;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Club> findById(Long id){
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
    public Optional<Club> findByName(String name){
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
    public Optional<Club> findByIdAndUsername(Long id, String username) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(club)
                                .where(club.id.eq(id)
                                        .and(club.username.eq(username))
                                        .and(club.isDeleted.eq(false))) // 삭제되지 않은 동호회만 조회
                                .fetchOne()
                );
    }
}

