package com.team6.finalproject.user.controller;

import com.team6.finalproject.advice.custom.DuplicateNameException;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.dto.*;
import com.team6.finalproject.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "user 컨트롤러입니다.")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) throws DuplicateNameException {
        userService.signup(signupRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/api/my-page")
    public String myPage() {
        return "my-page";
    }

    @GetMapping("/api/update-password")
    public String updatePW(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        return "update-password";
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

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        session.invalidate();
    }

    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "withdrawal";
    }
}
