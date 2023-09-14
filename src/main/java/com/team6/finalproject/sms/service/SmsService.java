package com.team6.finalproject.sms.service;

import com.team6.finalproject.common.dto.ApiResponseDto;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;

public interface SmsService {

    public SingleMessageSentResponse smsSend(String phoneNumber);

    public void checkSmsCertification(String phoneNumber, String key);

}
