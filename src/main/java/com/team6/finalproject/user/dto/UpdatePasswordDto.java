package com.team6.finalproject.user.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordDto {
    private String currentPassword;
    private String newPassword;
    private String checkPassword;
}
