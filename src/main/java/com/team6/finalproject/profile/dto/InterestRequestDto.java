package com.team6.finalproject.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter // 테스트에서 사용
public class InterestRequestDto {
    private List<Long> minorId;
}
