package com.team6.finalproject.user.dto;

import com.team6.finalproject.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String userName;
    private String password;
    private String email;
    private String birth;
    private UserRoleEnum role;
}
