package com.team6.finalproject.user.service;

import com.team6.finalproject.user.dto.SignupRequestDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //회원가입
    public void signup(SignupRequestDto signupRequestDto);
    public boolean findPhoneNumber(String phoneNumber);
    public void saveUser(User user);
}
