package com.team6.finalproject.profile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 테스트에서 사용
public class ProfileRequestDto {
    private String nickname;
    private String introduction;
    private String latitude;
    private String longitude;
    private String zoneCode;
    private String locate;
}
