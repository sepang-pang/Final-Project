package com.team6.finalproject.user.inquiry.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.inquiry.dto.InquiryRequestDto;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
import com.team6.finalproject.user.inquiry.entity.Inquiry;
import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
import com.team6.finalproject.user.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final FileUploader fileUploader;

    @Override
    @Transactional // 문의 등록
    public InquiryResponseDto createInquiry(InquiryRequestDto requestDto, MultipartFile file, User user) throws IOException {

        String media = fileUploader.upload(file);

        Inquiry inquiry = Inquiry.builder()
                .inquiryType(requestDto.getInquiryType())
                .description(requestDto.getDescription())
                .media(media)
                .user(user)
                .build();
        inquiryRepository.save(inquiry);
        return new InquiryResponseDto(inquiry);
    }

    @Override
    @Transactional(readOnly = true) // 문의 단건 조회
    public InquiryResponseDto getInquiry(Long inquiryId, User user) throws NotExistResourceException {
        Inquiry inquiry = findByIdAndUserId(inquiryId, user.getId());
        return new InquiryResponseDto(inquiry);
    }

    @Override
    @Transactional(readOnly = true) // 문의 전체 조회
    public List<InquiryResponseDto> getAllInquiry(User user) {
        return inquiryRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(InquiryResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional // 문의 수정
    public InquiryResponseDto updateInquiry(InquiryRequestDto requestDto, MultipartFile file, User user) throws NotExistResourceException, IOException {
        Inquiry inquiry = findByIdAndUserId(requestDto.getInquiryId(), user.getId());

        // 본인 문의만 수정 가능
        if (!inquiry.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("본인 문의만 수정 가능합니다.");
        }

        if (inquiry.getMedia() != null) {
           fileUploader.deleteFile(inquiry.getMedia());
        }

        String media = fileUploader.upload(file);
        String description = requestDto.getDescription();
        InquiryTypeEnum inquiryType = requestDto.getInquiryType();

        inquiry.update(media, inquiryType, description);
        return new InquiryResponseDto(inquiry);
    }

    @Override
    @Transactional
    public void deleteInquiry(Long inquiryId, User user) throws NotExistResourceException {
        Inquiry inquiry = findByIdAndUserId(inquiryId, user.getId());
        inquiry.deleteInquiry();
    }

    private Inquiry findByIdAndUserId(Long id, Long userId) throws NotExistResourceException {
        // 로그인 유저 문의 찾기
        return inquiryRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new NotExistResourceException("문의 내역을 찾을 수 없습니다."));
    }
}
