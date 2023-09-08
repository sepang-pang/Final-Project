package com.team6.finalproject.admin.answer.service;

import com.team6.finalproject.admin.answer.dto.AnswerRequestDto;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface InquiryAnswerService {
    InquiryResponseDto inquiryAnswer(User user, AnswerRequestDto requestDto, Long inquiryId) throws AccessDeniedException, NotExistResourceException;

    List<InquiryResponseDto> getAllInquiry(User user) throws AccessDeniedException;
}
