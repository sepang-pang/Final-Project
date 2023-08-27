package com.team6.finalproject.profile.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InterestRequestDto {
    private List<Long> minorId;
}
