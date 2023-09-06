package com.team6.finalproject.user.controller;

import com.team6.finalproject.advice.custom.DuplicateNameException;
import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.dto.InquiryRequestDto;
import com.team6.finalproject.user.dto.InquiryResponseDto;
import com.team6.finalproject.user.dto.SignupRequestDto;
import com.team6.finalproject.user.inquiry.service.InquiryService;
import com.team6.finalproject.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session =httpServletRequest.getSession();

        session.invalidate();
    }

    @GetMapping("/withdrawal")
    public String  withdrawal() {
        return "withdrawal";
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
