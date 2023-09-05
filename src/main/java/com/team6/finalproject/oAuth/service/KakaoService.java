package com.team6.finalproject.oAuth.service;

import com.team6.finalproject.oAuth.dto.Access_tokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "kakao 서비스입니다.")
public class KakaoService {

    private final RestTemplate restTemplate;


    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    public void tlscjd(String code) {
        String apiUrl = "https://kauth.kakao.com/oauth/token";
        String grantType = "authorization_code";
        String clientId = "4284645c387b0d5bb7d529cf9658e8bc";
        String redirectUri = KAKAO_REDIRECT_URL;
//            String authorizeCode = "${AUTHORIZE_CODE}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String requestBody = "grant_type=" + grantType +
                "&client_id=" + clientId +
                "&redirect_uri=" + "http://localhost:8080/kakao/callback" +
                "&code=" + code;
        log.info(requestBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        Access_tokenResponseDto response = restTemplate.postForObject(
                apiUrl,
                requestEntity,
                Access_tokenResponseDto.class
        );

        log.info(response.getAccess_token());

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer ${ACCESS_TOKEN}");
    }


//    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
//    private final static String KAKAO_API_URI = "https://kapi.kakao.com";
//
//    public String getKakaoLogin() {
//        return KAKAO_AUTH_URI + "/oauth/authorize"
//                + "?client_id=" + KAKAO_CLIENT_ID
//                + "&redirect_uri=" + KAKAO_REDIRECT_URL
//                + "&response_type=code";
//    }

}