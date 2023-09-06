package com.team6.finalproject.user.dto;

import com.team6.finalproject.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String birth;
    private int age;
    private UserRoleEnum role;
}
