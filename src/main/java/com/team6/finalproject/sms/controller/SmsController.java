package com.team6.finalproject.sms.controller;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.sms.dto.PhoneNumberDto;
import com.team6.finalproject.sms.dto.SmsCertificationDto;
import com.team6.finalproject.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public SingleMessageSentResponse sendSms(@RequestBody PhoneNumberDto phoneNumber) {
        return smsService.smsSend(phoneNumber.getPhoneNumber());
    }

    // 휴대폰 인증 번호 확인
    @PostMapping("/check")
    public void checkSmsCertification(@RequestBody SmsCertificationDto smsCertificationDto) {
        smsService.checkSmsCertification(smsCertificationDto.getPhoneNumber(), smsCertificationDto.getVerificationCode());
    }
}