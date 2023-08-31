package com.team6.finalproject.oAuth.service;

import com.team6.finalproject.oAuth.dto.Access_tokenResponseDto;
import com.team6.finalproject.oAuth.dto.OAuthSignupRequestDto;
import com.team6.finalproject.oAuth.dto.UserinfoResponseDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "kakao 서비스입니다.")
public class KakaoService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;


    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    public boolean tlscjd(String code,HttpServletResponse httpServletResponse) {
        String apiUrl = "https://kauth.kakao.com/oauth/token";
        String grantType = "authorization_code";
        String clientId = "4284645c387b0d5bb7d529cf9658e8bc";
        String redirectUri = "${REDIRECT_URI}";
//            String authorizeCode = "${AUTHORIZE_CODE}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String requestBody = "grant_type=" + grantType +
                "&client_id=" + clientId +
                "&redirect_uri=" + "http://localhost:8080/kakao/callback" +
                "&code=" + code;
        log.info(requestBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        Access_tokenResponseDto access_token = restTemplate.postForObject(
                apiUrl,
                requestEntity,
                Access_tokenResponseDto.class
        );

        log.info("엑세스토큰 가져오기 성공 : "+access_token.toString());

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers1.setBearerAuth(access_token.getAccess_token());

        String requestBody1 = "property_keys=[\"kakao_account.email\"]";

        HttpEntity<String> requestEntity1 = new HttpEntity<>(requestBody1, headers1);

        Map<String,Object> reponseUserinfo = restTemplate.postForObject(
                "https://kapi.kakao.com/v2/user/me",
                requestEntity1,
                Map.class
        );
        String email =(String) ((Map<String,Object>) reponseUserinfo.get("kakao_account")).get("email");

        if (userRepository.findByEmail(email).isEmpty()) {
            httpServletResponse.addHeader("email",email);
            return false;
        }
        return true;
    }

    public void oAuthSignup(OAuthSignupRequestDto oAuthSignupRequestDto, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {

        String email = oAuthSignupRequestDto.getEmail();
        log.info(email);

        User user = User.builder()
                .username(oAuthSignupRequestDto.getUsername())
                .birth(oAuthSignupRequestDto.getBirth())
                .email(email)
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);

        httpServletResponse.addHeader("username",oAuthSignupRequestDto.getUsername());
        log.info("oooo");
    }
}

// 받은 유저 이메일을 REPOSITORY에서 조회를 한 후 있으면 main 페이지 반환.
// 없으면 추가 정보 입력 창으로 이동 후 이메일 반환해서 인증세션 만들고 난 후 에 main 페이지 반환.