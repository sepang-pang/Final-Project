package com.team6.finalproject.user.inquiry.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.inquiry.dto.InquiryRequestDto;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InquiryService {
    // 문의 전체 조회
    List<InquiryResponseDto> getAllInquiry(User user);

    // 문의 상세 조회
    InquiryResponseDto getInquiry(Long inquiryId, User user) throws NotExistResourceException;

    // 문의 생성
    InquiryResponseDto createInquiry(InquiryRequestDto requestDto, MultipartFile file, User user) throws IOException;

    // 문의 수정
    public InquiryResponseDto updateInquiry(InquiryRequestDto requestDto, MultipartFile file, User user) throws NotExistResourceException, IOException;

    // 문의 삭제
    void deleteInquiry(Long inquiryId, User user) throws NotExistResourceException;
}
