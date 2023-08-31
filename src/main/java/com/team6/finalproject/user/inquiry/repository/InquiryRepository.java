package com.team6.finalproject.user.inquiry.repository;

import com.team6.finalproject.user.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {
    Optional<Inquiry> findByIdAndUserId(Long id, Long userId);

    List<Inquiry> findAllByUserIdOrderByCreatedAtDesc(Long id);
}
