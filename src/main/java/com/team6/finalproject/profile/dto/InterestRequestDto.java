package com.team6.finalproject.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder // 테스트에서 사용
@NoArgsConstructor
@AllArgsConstructor
public class InterestRequestDto {
    private List<Long> minorId;
}
