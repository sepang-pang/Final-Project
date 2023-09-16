package com.team6.finalproject.club.interest.dto;

import com.team6.finalproject.club.interest.entity.InterestMinor;
import lombok.Getter;

@Getter
public class InterestMinorAllDto {
    private Long interestMinorId;
    private Long interestMajorId;
    private String interestMinorName;

    public InterestMinorAllDto(InterestMinor interestMinor) {
        this.interestMinorId = interestMinor.getId();
        this.interestMajorId = interestMinor.getInterestMajor().getId();
        this.interestMinorName = interestMinor.getMinorName();
    }
}
