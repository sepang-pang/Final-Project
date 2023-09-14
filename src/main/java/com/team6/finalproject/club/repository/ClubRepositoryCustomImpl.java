package com.team6.finalproject.club.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.team6.finalproject.club.entity.QClub.club;
import static com.team6.finalproject.club.member.entity.QMember.member;

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

    @Override // 유저의 관심사와 같은 관심사를 가진 동호회
    public List<Club> findClubsByUserInterestMinor(List<Long> userInterestIds) {
        return
                jpaQueryFactory
                        .selectFrom(club)
                        .where(club.minor.id.in(userInterestIds)
                                .and(club.isDeleted.eq(false)))
                        .fetch();
    }

    @Override // 연령대 안에 유저의 나이가 포함된 동호회
    public List<Club> findClubsByUserAge(int userAge) {
        return
                jpaQueryFactory
                        .selectFrom(club)
                        .where(club.minAge.loe(userAge)
                                .and(club.maxAge.goe(userAge))
                                .and(club.isDeleted.eq(false)))
                        .fetch();
    }

    @Override // 최근 개설된 동호회 조회
    public List<Club> findClubsByRecent() {
        return
                jpaQueryFactory
                        .selectFrom(club)
                        .where(club.isDeleted.eq(false))
                        .orderBy(club.createdAt.desc())
                        .fetch();
    }

    @Override // 인기 동호회 추천
    public List<Club> findPopularClubs() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.with(LocalTime.of(6, 0));
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return
                jpaQueryFactory
                        .selectFrom(club)
                        .leftJoin(member)
                        .on(club.id.eq(member.club.id))
                        .where(club.isDeleted.eq(false),// 삭제되지 않은 동호회만 조회
                                club.activityScore.goe(60)
                                        .and(club.maxMember.multiply(0.3).loe(jpaQueryFactory.select(member.count())
                                                .from(member)
                                                .where(member.club.eq(club)
                                                        .and(member.createdAt.between(startOfDay, endOfDay)))))
                        )
                        .groupBy(club.id)
                        .orderBy(member.count().desc())
                        .limit(5)
                        .fetch();
    }

    @Override
    public List<Club> findRecommendedClubs(User user) {
        return jpaQueryFactory.selectFrom(club)
                .where(
                        club.isDeleted.eq(false), // 삭제되지 않은 동호회만 조회
                        club.minAge.loe(user.getAge()), // 최소 연령
                        club.maxAge.goe(user.getAge()), // 최대 연령
                        club.minor.in(user.getProfile().getProfileInterests().stream()
                                .map(ProfileInterest::getInterestMinor)
                                .collect(Collectors.toList()))   // 관심사 일치
                )
                .fetch();
    }

    // 내가 개설한 동호회
    @Override
    public List<Club> findMyClubs(User user) {
        return jpaQueryFactory.selectFrom(club)
                .where(club.isDeleted.eq(false)
                        .and(club.username.eq(user.getUsername())))
                .orderBy(club.createdAt.desc())
                .fetch();
    }

    // 찜한 동호회
    @Override
    public List<Club> findLikeClubs(Profile profile) {
        return jpaQueryFactory.selectFrom(club)
                .where(club.likeClubs.any().profile.eq(profile) // 찜 동호회 목록에 해당 프로필이 있는지 확인
                        .and(club.isDeleted.eq(false)))
                .fetch();
    }
}
