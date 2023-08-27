package com.team6.finalproject.profile.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder // 테스트에서 사용
public class InterestRequestDto {
    private List<Long> minorId;
}
