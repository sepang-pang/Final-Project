package com.team6.finalproject.admin.answer.service;

import com.team6.finalproject.admin.answer.dto.AnswerRequestDto;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;

import java.nio.file.AccessDeniedException;

public interface InquiryAnswerService {
    InquiryResponseDto inquiryAnswer(User user, AnswerRequestDto requestDto, Long inquiryId) throws AccessDeniedException, NotExistResourceException;
}
