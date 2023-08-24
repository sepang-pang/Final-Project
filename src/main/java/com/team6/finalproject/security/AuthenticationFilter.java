package com.team6.finalproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team6.finalproject.user.dto.UserRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "Authentication 필터입니다.")
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public AuthenticationFilter() {
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Authentication 필터입니다.");

        try {
            UserRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 세션 생성");
        HttpSession session = request.getSession();
        session.setAttribute("username", authResult.getPrincipal());
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
//
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
//        SecurityContextHolder.setContext(context);
//        log.info(userDetails.getUsername());
    }
}
