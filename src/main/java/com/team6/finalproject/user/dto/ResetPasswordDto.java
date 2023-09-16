package com.team6.finalproject.user.dto;

import lombok.Getter;

@Getter
public class ResetPasswordDto {
    private String newPassword;
    private String checkPassword;
    private String username;
}
