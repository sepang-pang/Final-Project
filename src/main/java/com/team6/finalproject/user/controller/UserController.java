package com.team6.finalproject.user.controller;

import com.team6.finalproject.advice.custom.DuplicateNameException;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.dto.*;
import com.team6.finalproject.user.inquiry.service.InquiryService;
import com.team6.finalproject.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "user 컨트롤러입니다.")
public class UserController {

    private final UserService userService;
    private final InquiryService inquiryService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) throws DuplicateNameException {
        userService.signup(signupRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/aa")
    public String aa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(userDetails.getUsername());
        return "main";
    }

    @PatchMapping("/api/users/password") // 비밀번호 재설정
    @ResponseBody
    public ResponseEntity<ApiResponseDto> updatePassword(@RequestBody UpdatePasswordDto passwordDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(passwordDto, userDetails.getUser());
        return ResponseEntity.ok(new ApiResponseDto("비밀번호가 변경되었습니다.", HttpStatus.OK.value()));
    }

    @PostMapping("/api/users/id-inquiry") // ID 찾기
    @ResponseBody
    public ResponseEntity<ApiResponseDto> idInquiry(@RequestBody EmailRequestDto requestDto)
            throws NotExistResourceException, MessagingException {
        userService.idInquiry(requestDto);
        return ResponseEntity.ok(new ApiResponseDto("인증 메일이 발송되었습니다.", HttpStatus.OK.value()));
    }

    @PostMapping("/api/users/id-auth") // ID 찾기 인증
    @ResponseBody
    public IdResponseDto returnId(@RequestBody AuthRequestDto requestDto) throws NotExistResourceException {
        if (userService.verifyCode(requestDto)) { // 인증코드 검증

            // 인증 성공 시 ID 반환 -> 비밀번호 재설정 페이지로 넘어가는 버튼 달기
            return userService.returnId(requestDto.getEmail());
        }
        throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");
    }

    @PostMapping("/api/users/pw-inquiry") // 비밀번호 찾기
    @ResponseBody
    public ResponseEntity<ApiResponseDto> passwordInquiry(@RequestBody PasswordInquiryDto inquiryDto)
            throws NotExistResourceException, MessagingException {
        userService.passwordInquiry(inquiryDto);
        return ResponseEntity.ok(new ApiResponseDto("인증 메일이 발송되었습니다.", HttpStatus.OK.value()));
    }

    @PostMapping("/api/users/pw-auth") // 비밀번호 찾기 인증
    @ResponseBody
    public ResponseEntity<ApiResponseDto> returnPasswordAfterVerify(@RequestBody AuthRequestDto requestDto)
            throws NotExistResourceException {
        if (userService.verifyCode(requestDto)) { // 인증코드 검증

            // 비밀번호 재설정 페이지 반환 예정
            return ResponseEntity.ok(new ApiResponseDto("비밀번호 재설정 페이지 반환 예정", HttpStatus.OK.value()));
        }
        throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");
    }

    @PostMapping("/api/users/inquiry") // 문의 생성
    @ResponseBody
    public InquiryResponseDto createInquiry(@RequestBody InquiryRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return inquiryService.createInquiry(requestDto, userDetails.getUser());
    }

    @GetMapping("/api/users/inquiry/{id}") // 문의 단건 조회
    @ResponseBody
    public InquiryResponseDto getInquiry(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return inquiryService.getInquiry(id, userDetails.getUser());
    }

    @GetMapping("/api/users/all-inquiry") // 문의 전체 조회
    @ResponseBody
    public List<InquiryResponseDto> getAllInquiry(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return inquiryService.getAllInquiry(userDetails.getUser());
    }

    @PatchMapping("/api/users/inquiry") // 문의 수정
    @ResponseBody
    public InquiryResponseDto updateInquiry(@RequestBody InquiryRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return inquiryService.updateInquiry(requestDto, userDetails.getUser());
    }
}
