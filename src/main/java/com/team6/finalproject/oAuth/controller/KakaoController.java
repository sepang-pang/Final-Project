package com.team6.finalproject.oAuth.controller;

import com.team6.finalproject.oAuth.dto.OAuthSignupRequestDto;
import com.team6.finalproject.oAuth.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code, HttpServletResponse httpServletResponse, Model model){
        if (kakaoService.check(code,httpServletResponse)) {
            model.addAttribute("email",httpServletResponse.getHeader("email"));
            return "main";
        }else {
            model.addAttribute("email",httpServletResponse.getHeader("email"));
            return "oAuthSignup";
        }
    }

    @PostMapping("/oauthsignup")
    public String oAuthSignup(@RequestBody OAuthSignupRequestDto oAuthSignupRequestDto,HttpServletResponse httpServletResponse) {
        kakaoService.oAuthSignup(oAuthSignupRequestDto,httpServletResponse);
        return "main";
    }
}
