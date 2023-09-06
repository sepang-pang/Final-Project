package com.team6.finalproject.sms.service.redis;

import com.team6.finalproject.sms.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final String PREFIX = "sms:";
    private final String CODE_FIELD = "code";
    private final String VERIFIED_FIELD = "isVerified";
    private final int LIMIT_TIME = 5 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void createSmsCertification(String phone, String certificationNumber) {
        stringRedisTemplate.opsForHash().put(PREFIX + phone, CODE_FIELD, certificationNumber);
        stringRedisTemplate.opsForHash().put(PREFIX + phone, VERIFIED_FIELD, "false");
        stringRedisTemplate.expire(PREFIX + phone, Duration.ofSeconds(LIMIT_TIME));
    }

    @Override
    public String getSmsCertification(String phone) { // 인증번호 가져오기
        return (String) stringRedisTemplate.opsForHash().get(PREFIX + phone, CODE_FIELD);
    }

    @Override
    public boolean isVerified(String phone) { // 인증 여부 확인 -> true 면 인증 완료
        String value = (String) stringRedisTemplate.opsForHash().get(PREFIX + phone, VERIFIED_FIELD);
        return "true".equals(value);
    }

    @Override
    public void markAsVerified(String phone) { // 인증 완료 처리
        stringRedisTemplate.opsForHash().put(PREFIX + phone, VERIFIED_FIELD, "true");
    }

    @Override
    public void removeSmsCertification(String phone) { // 인증번호 삭제
        stringRedisTemplate.delete(PREFIX + phone);
    }

    @Override
    public boolean hasKey(String phone) { // 인증번호 존재 여부
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(PREFIX + phone));
    }

}
