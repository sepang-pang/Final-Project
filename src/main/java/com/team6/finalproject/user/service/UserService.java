package com.team6.finalproject.user.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.dto.*;
import com.team6.finalproject.user.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    // 회원가입
    void signup(SignupRequestDto signupRequestDto);
    // 유저저장
    void saveUser(User user);
    // ID 찾기
    void idInquiry(EmailRequestDto requestDto) throws NotExistResourceException, MessagingException;
    // 인증코드 확인
    boolean verifyCode(AuthRequestDto requestDto) throws NotExistResourceException;
    // 인증 후 ID 반환
    IdResponseDto returnId(String email) throws NotExistResourceException;
    // 비밀번호 찾기
    void passwordInquiry(PasswordInquiryDto inquiryDto) throws NotExistResourceException, MessagingException;
    // 인증 후 임시 비밀번호 발송
    void returnPassword(String email) throws MessagingException, NotExistResourceException;
}
