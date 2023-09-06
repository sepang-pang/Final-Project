package com.team6.finalproject.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.user.entity.QUser;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByActivePhone(String phone) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(user)
                                .where(user.phone.eq(phone)
                                        .and(user.isDeleted.eq(false)))
                                .fetchOne()
                );
    }
}
