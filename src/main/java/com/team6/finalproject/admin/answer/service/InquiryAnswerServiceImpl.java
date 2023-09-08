package com.team6.finalproject.admin.answer.service;

import com.team6.finalproject.admin.answer.dto.AnswerRequestDto;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
import com.team6.finalproject.user.inquiry.entity.Inquiry;
import com.team6.finalproject.user.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class InquiryAnswerServiceImpl implements InquiryAnswerService {

    private final InquiryRepository inquiryRepository;

    @Override
    @Transactional
    public InquiryResponseDto inquiryAnswer(User user, AnswerRequestDto requestDto, Long inquiryId)
            throws AccessDeniedException, NotExistResourceException {
        // 관리자 권한 확인
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(
                () -> new NotExistResourceException("문의 내역을 찾을 수 없습니다.")
        );

        inquiry.saveAnswer(requestDto.getAnswer());
        return new InquiryResponseDto(inquiry);
    }
}