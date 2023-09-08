package com.team6.finalproject.user.inquiry.controller;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.inquiry.dto.InquiryRequestDto;
import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
import com.team6.finalproject.user.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("/api/inquiry") // 문의 생성
    @ResponseBody
    public InquiryResponseDto createInquiry(@RequestBody InquiryRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return inquiryService.createInquiry(requestDto, userDetails.getUser());
    }

    @GetMapping("/api/inquiry/{id}") // 문의 단건 조회
    @ResponseBody
    public InquiryResponseDto getInquiry(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return inquiryService.getInquiry(id, userDetails.getUser());
    }

    @GetMapping("/api/all-inquiry") // 문의 전체 조회
    @ResponseBody
    public List<InquiryResponseDto> getAllInquiry(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return inquiryService.getAllInquiry(userDetails.getUser());
    }

    @PatchMapping("/api/inquiry") // 문의 수정
    @ResponseBody
    public InquiryResponseDto updateInquiry(@RequestBody InquiryRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return inquiryService.updateInquiry(requestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/inquiry/{inquiryId}")
    public ResponseEntity<ApiResponseDto> deleteInquiry(@PathVariable Long inquiryId, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws NotExistResourceException {
        inquiryService.deleteInquiry(inquiryId, userDetails.getUser());
        return ResponseEntity.ok(new ApiResponseDto("문의 삭제 완료", HttpStatus.OK.value()));
    }
}
