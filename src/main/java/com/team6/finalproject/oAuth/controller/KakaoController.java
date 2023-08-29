package com.team6.finalproject.oAuth.controller;

import com.team6.finalproject.oAuth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @ResponseBody
    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam String code){
        kakaoService.tlscjd(code);
    }
}
