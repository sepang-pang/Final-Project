package com.team6.finalproject.club.dto;

import com.team6.finalproject.interest.entity.InterestMajor;
import lombok.Getter;

@Getter
public class InterestMajorDto {
    private Long id;
    private String name;

    public InterestMajorDto(InterestMajor interestMajor) {
        this.id = interestMajor.getId();
        this.name = interestMajor.getMajorName();
    }
}
