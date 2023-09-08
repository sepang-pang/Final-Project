package com.team6.finalproject.user.inquiry.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.inquiry.dto.InquiryRequestDto;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
import com.team6.finalproject.user.inquiry.entity.Inquiry;

import java.util.List;

public interface InquiryService {
    // 문의 생성
    InquiryResponseDto createInquiry(InquiryRequestDto requestDto, User user);
    // 문의 단건 조회
    InquiryResponseDto getInquiry(Long inquiryId, User user) throws NotExistResourceException;
    // 문의 전체 조회
    List<InquiryResponseDto> getAllInquiry(User user);
    // 문의 수정
    InquiryResponseDto updateInquiry(InquiryRequestDto requestDto, User user) throws NotExistResourceException;
    // 문의 삭제
    void deleteInquiry(Long inquiryId, User user) throws NotExistResourceException;
    // 로그인 유저의 문의 찾기
    Inquiry findByIdAndUserId(Long id, Long userId) throws NotExistResourceException;
}
