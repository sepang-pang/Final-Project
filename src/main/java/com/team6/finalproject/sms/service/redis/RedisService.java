package com.team6.finalproject.sms.service.redis;

public interface RedisService {

    public void createSmsCertification(String phone, String certificationNumber);

    public String getSmsCertification(String phone);

    public boolean isVerified(String phone);

    public void markAsVerified(String phone);

    public void removeSmsCertification(String phone);

    public boolean hasKey(String phone);

}
