package com.team6.finalproject.user.inquiry.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.user.inquiry.entity.Inquiry;
import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.user.entity.QUser.user;
import static com.team6.finalproject.user.inquiry.entity.QInquiry.inquiry;

@Repository
@RequiredArgsConstructor
public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Inquiry> findByIdAndUserId(Long id, Long userId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(inquiry)
                        .where(inquiry.id.eq(id)
                                .and(user.id.eq(userId))
                                .and(inquiry.isDeleted.eq(false)))
                        .fetchOne()
        );
    }

    @Override
    public List<Inquiry> findAllByUserIdOrderByCreatedAtDesc(Long userId) {
        return jpaQueryFactory
                .selectFrom(inquiry)
                .where(user.id.eq(userId)
                        .and(inquiry.isDeleted.eq(false)))
                .orderBy(inquiry.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Inquiry> findAllOrderByCreatedAtDesc() {
        return jpaQueryFactory
                .selectFrom(inquiry)
                .where(inquiry.isDeleted.eq(false))
                .orderBy(inquiry.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Inquiry> findAllByType(String inquiryType) {
        return jpaQueryFactory
                .selectFrom(inquiry)
                .where(inquiry.inquiryType.in(InquiryTypeEnum.valueOf(inquiryType))
                        .and(inquiry.isDeleted.eq(false)))
                .orderBy(inquiry.createdAt.desc())
                .fetch();
    }
}
