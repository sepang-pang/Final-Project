package com.team6.finalproject.user.dto;

import lombok.Getter;

@Getter
public class IdResponseDto {
    private String username;

    public IdResponseDto(String username) {
        this.username = username;
    }
}
