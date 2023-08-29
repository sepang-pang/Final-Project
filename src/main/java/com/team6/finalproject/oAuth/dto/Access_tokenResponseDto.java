package com.team6.finalproject.oAuth.dto;

import lombok.Getter;

@Getter
public class Access_tokenResponseDto {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer refresh_token_expires_in;
    private Integer expires_in;
    private String scope;

}
