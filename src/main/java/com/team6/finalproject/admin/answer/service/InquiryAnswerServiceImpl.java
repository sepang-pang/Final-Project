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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryAnswerServiceImpl implements InquiryAnswerService {

    private final InquiryRepository inquiryRepository;

    // 문의 답변 작성/수정
    @Override
    @Transactional
    public InquiryResponseDto inquiryAnswer(User user, AnswerRequestDto requestDto, Long inquiryId)
            throws AccessDeniedException, NotExistResourceException {
        checkRole(user); // 관리자 권한 확인
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(
                () -> new NotExistResourceException("문의 내역을 찾을 수 없습니다.")
        );

        inquiry.saveAnswer(requestDto.getAnswer());
        return new InquiryResponseDto(inquiry);
    }

    // 문의 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> getAllInquiry(User user) throws AccessDeniedException {
        checkRole(user); // 관리자 권한 확인
        return inquiryRepository.findAllOrderByCreatedAtDesc().stream()
                .map(InquiryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자 문의 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> getAllInquiryByUserId(Long userId, User user) throws AccessDeniedException {
        checkRole(user); // 관리자 권한 확인
        return inquiryRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(InquiryResponseDto::new)
                .collect(Collectors.toList());
    }

    private void checkRole(User user) throws AccessDeniedException {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
}
