package com.team6.finalproject.admin.answer.controller;

import com.team6.finalproject.admin.answer.dto.AnswerRequestDto;
import com.team6.finalproject.admin.answer.service.InquiryAnswerService;
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
public class InquiryAnswerController {

    private final InquiryAnswerService inquiryAnswerService;

    @PatchMapping("/inquiry/answer/{inquiryId}") // 문의 답변 작성/수정
    @ResponseBody
    public InquiryResponseDto inquiryAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AnswerRequestDto requestDto,
                                            @PathVariable Long inquiryId) throws AccessDeniedException, NotExistResourceException {
        return inquiryAnswerService.inquiryAnswer(userDetails.getUser(), requestDto, inquiryId);
    }

    @GetMapping("/all-inquiry") // 문의 전체 조회
    @ResponseBody
    public List<InquiryResponseDto> getAllInquiry(@AuthenticationPrincipal UserDetailsImpl userDetails) throws AccessDeniedException {
        return inquiryAnswerService.getAllInquiry(userDetails.getUser());
    }
}
