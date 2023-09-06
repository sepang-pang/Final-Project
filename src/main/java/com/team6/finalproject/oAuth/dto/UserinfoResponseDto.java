package com.team6.finalproject.oAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserinfoResponseDto {
    @JsonProperty("email")
    private String email;
}
