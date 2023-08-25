package com.team6.finalproject.profile.dto;

import lombok.Getter;

@Getter
public class ProfileNickNameDto {
    private String nickName;

    public ProfileNickNameDto(String nickname) {
        this.nickName = nickname;
    }
}
