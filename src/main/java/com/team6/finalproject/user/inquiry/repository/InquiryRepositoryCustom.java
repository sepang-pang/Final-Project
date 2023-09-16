package com.team6.finalproject.user.inquiry.repository;

import com.team6.finalproject.user.inquiry.entity.Inquiry;

import java.util.List;
import java.util.Optional;

public interface InquiryRepositoryCustom {
    // 로그인 사용자 문의 단건 조회
    Optional<Inquiry> findByIdAndUserId(Long id, Long userId);
    // 로그인 사용자 문의 전체 조회
    List<Inquiry> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    // 문의 전체 조회
    List<Inquiry> findAllOrderByCreatedAtDesc();
    // 문의유형 별 조회
    List<Inquiry> findAllByType(String inquiryType);
}
