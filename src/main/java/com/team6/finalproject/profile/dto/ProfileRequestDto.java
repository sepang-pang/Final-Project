package com.team6.finalproject.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // 테스트에서 사용
public class ProfileRequestDto {
    private String nickname;
    private String introduction;
    private String locate;
}
