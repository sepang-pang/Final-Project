package com.team6.finalproject.club.interest.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team6.finalproject.club.interest.entity.QInterestMinor.interestMinor;

@Repository
@RequiredArgsConstructor
public class InterestMinorRepositoryCustomImpl implements InterestMinorRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<InterestMinor> findByMajorId(Long majorId) {
        return
                queryFactory
                        .selectFrom(interestMinor)
                        .where(interestMinor.interestMajor.id.eq(majorId))
                        .orderBy(interestMinor.id.asc())
                        .fetch();
    }
}
