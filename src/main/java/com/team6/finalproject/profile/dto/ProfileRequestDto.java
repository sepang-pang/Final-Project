package com.team6.finalproject.profile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 테스트에서 사용
public class ProfileRequestDto {
    private String nickname;
    private String introduction;
    private Double latitude;
    private Double longitude;
    private String locate;
}
