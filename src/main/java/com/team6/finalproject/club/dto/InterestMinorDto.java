package com.team6.finalproject.club.dto;

import com.team6.finalproject.club.interest.entity.InterestMinor;
import lombok.Getter;

@Getter
public class InterestMinorDto {
    private Long id;
    private String name;

    public InterestMinorDto(InterestMinor minor) {
        this.id = minor.getId();
        this.name = minor.getMinorName();
    }
}
