package com.team6.finalproject.sms.service.sms;

import com.team6.finalproject.common.dto.ApiResponseDto;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SmsService {

    public SingleMessageSentResponse smsSend(String phoneNumber);

    public ResponseEntity<ApiResponseDto> checkSmsCertification(String phoneNumber, String key);

}
