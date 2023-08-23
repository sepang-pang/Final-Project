package com.team6.finalproject.user.dto;

import com.team6.finalproject.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String username;
    private String password;
}
