package com.team6.finalproject.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final StringRedisTemplate stringRedisTemplate;
    private final String PREFIX = "sms:";
    private final String CODE_FIELD = "code";
    private final String VERIFIED_FIELD = "isVerified";
    private final int LIMIT_TIME = 5 * 60;

    public void createSmsCertification(String phone, String certificationNumber) {
        stringRedisTemplate.opsForHash().put(PREFIX + phone, CODE_FIELD, certificationNumber);
        stringRedisTemplate.opsForHash().put(PREFIX + phone, VERIFIED_FIELD, "false");
        stringRedisTemplate.expire(PREFIX + phone, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getSmsCertification(String phone) { // 인증번호 가져오기
        return (String) stringRedisTemplate.opsForHash().get(PREFIX + phone, CODE_FIELD);
    }

    public boolean isVerified(String phone) { // 인증 여부 확인 -> true 면 인증 완료
        String value = (String) stringRedisTemplate.opsForHash().get(PREFIX + phone, VERIFIED_FIELD);
        return "true".equals(value);
    }

    public void markAsVerified(String phone) { // 인증 완료 처리
        stringRedisTemplate.opsForHash().put(PREFIX + phone, VERIFIED_FIELD, "true");
    }

    public void removeSmsCertification(String phone) { // 인증번호 삭제
        stringRedisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) { // 인증번호 존재 여부
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(PREFIX + phone));
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}