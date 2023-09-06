package com.team6.finalproject.sms.service.sms;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.sms.service.redis.RedisService;
import com.team6.finalproject.sms.service.sms.SmsService;
import com.team6.finalproject.user.service.UserService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public class SmsServiceImpl implements SmsService {
    private final DefaultMessageService messageService;
    private final UserService userService;
    private final RedisService redisService;
    private String SMS_API_KEY = "NCS6FVFQXPQRTSO5";
    private String SMS_SECRET_KEY = "WEMTKUIDHBOKOX3LLTI6CSOXDEWGMA0R";
    private String DOMAIN = "https://api.coolsms.co.kr";

    public SmsServiceImpl(UserService userService, RedisService redisService) {
        this.redisService = redisService;
        this.userService =  userService;
        this.messageService = NurigoApp.INSTANCE.initialize(SMS_API_KEY, SMS_SECRET_KEY, DOMAIN);
    }

    @Override
    @Transactional
    public SingleMessageSentResponse smsSend(String phoneNumber) {

        // 이미 가입한 번호인지 확인
        if (userService.findPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("이미 가입된 번호입니다.");
        }

        String key = createKey();

        Message message = new Message();
        message.setFrom("01099290533");
        message.setTo(phoneNumber);
        message.setText("미쯔(meets) 인증번호는 [" + key + "] 입니다.");

        // 메시지 보내기
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        if (redisService.hasKey(phoneNumber)) { // 이미 존재하면 삭제
            redisService.removeSmsCertification(phoneNumber);
        }

        redisService.createSmsCertification(phoneNumber, key); // redis 에 저장

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> checkSmsCertification(String phoneNumber, String key) {
        String redisKey = redisService.getSmsCertification(phoneNumber);

        if (!redisService.hasKey(phoneNumber)) { // 인증번호 유효성 검사
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }

        if(redisService.isVerified(phoneNumber)) { // 중복 인증 예외
            throw new IllegalArgumentException("이미 인증된 번호입니다.");
        }

        if(!redisKey.equals(key)) { // 불일치 예외
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        redisService.markAsVerified(phoneNumber); // 인증 완료

        return ResponseEntity.ok().body(new ApiResponseDto("인증 완료", 200));
    }

    public String createKey() { // 키 생성 함수
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            key.append((int) (Math.random() * 10));
        }
        return key.toString();
    }

}
