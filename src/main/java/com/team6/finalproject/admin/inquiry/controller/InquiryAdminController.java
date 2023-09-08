package com.team6.finalproject.admin.inquiry.controller;

import com.team6.finalproject.admin.inquiry.dto.AnswerRequestDto;
import com.team6.finalproject.admin.inquiry.service.InquiryAdminService;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class InquiryAdminController {

    private final InquiryAdminService inquiryAdminService;

    @PatchMapping("/inquiry/answer/{inquiryId}") // 문의 답변 작성/수정
    @ResponseBody
    public InquiryResponseDto inquiryAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AnswerRequestDto requestDto,
                                            @PathVariable Long inquiryId) throws AccessDeniedException, NotExistResourceException {
        return inquiryAdminService.inquiryAnswer(userDetails.getUser(), requestDto, inquiryId);
    }

    @GetMapping("/all-inquiry") // 문의 전체 조회
    @ResponseBody
    public List<InquiryResponseDto> getAllInquiry(@AuthenticationPrincipal UserDetailsImpl userDetails) throws AccessDeniedException {
        return inquiryAdminService.getAllInquiry(userDetails.getUser());
    }

    @GetMapping("/inquiry/{userId}") // 문의 유저 선택 조회
    @ResponseBody
    public List<InquiryResponseDto> getAllInquiryByUserId(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws AccessDeniedException {
        return inquiryAdminService.getAllInquiryByUserId(userId, userDetails.getUser());
    }

    @GetMapping("/inquiry/type") // 문의유형 별 전체 조회
    @ResponseBody
    public List<InquiryResponseDto> getAllInquiryByType(@RequestParam String inquiryType, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws AccessDeniedException {
        return inquiryAdminService.getAllInquiryByType(inquiryType, userDetails.getUser());
    }
}
