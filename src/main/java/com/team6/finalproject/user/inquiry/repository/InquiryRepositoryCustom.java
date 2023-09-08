package com.team6.finalproject.user.inquiry.repository;

import com.team6.finalproject.user.inquiry.entity.Inquiry;

import java.util.List;
import java.util.Optional;

public interface InquiryRepositoryCustom {
    Optional<Inquiry> findByIdAndUserId(Long id, Long userId);

    List<Inquiry> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
