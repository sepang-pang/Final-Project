package com.team6.finalproject.admin.inquiry.service;

import com.team6.finalproject.admin.inquiry.dto.AnswerRequestDto;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface InquiryAdminService {
    // 문의 답변 작성/수정
    InquiryResponseDto inquiryAnswer(User user, AnswerRequestDto requestDto, Long inquiryId) throws AccessDeniedException, NotExistResourceException;
    // 문의 전체 조회
    List<InquiryResponseDto> getAllInquiry(User user) throws AccessDeniedException;
    // 특정 사용자 문의 전체 조회
    List<InquiryResponseDto> getAllInquiryByUserId(Long userId, User user) throws AccessDeniedException;
    // 문의유형 별 전체 조회
    List<InquiryResponseDto> getAllInquiryByType(String inquiryType, User user) throws AccessDeniedException;
}
