package com.team6.finalproject.user.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.user.dto.*;
import com.team6.finalproject.user.entity.User;
import jakarta.mail.MessagingException;

public interface UserService {
    // 회원가입
    public void signup(SignupRequestDto signupRequestDto);
    // 폰번호 찾기
    public boolean findPhoneNumber(String phoneNumber);
    // 유저저장
    public void saveUser(User user);
    // ID 찾기
    void idInquiry(EmailRequestDto requestDto) throws NotExistResourceException, MessagingException;
    // 인증코드 확인
    boolean verifyCode(AuthRequestDto requestDto) throws NotExistResourceException;
    // 인증 후 ID 반환
    IdResponseDto returnId(String email) throws NotExistResourceException;
    // 비밀번호 찾기
    void passwordInquiry(PasswordInquiryDto inquiryDto) throws NotExistResourceException, MessagingException;
    // 비밀번호 재설정
    void updatePassword(UpdatePasswordDto passwordDto, User user);
    // 이메일으로 유저 찾기
    User findByEmail(String email) throws NotExistResourceException;

    // Id로 유저 찾기
    public User findByUser(Long id) throws NotExistResourceException;
}
