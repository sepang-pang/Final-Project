package com.team6.finalproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team6.finalproject.user.dto.UserRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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


        if (request.getRequestURI().equals("/api/login")) {
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

        }else {
            try {
                String username = request.getHeader("username");

                return getAuthenticationManager().authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                null
                        )
                );
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("username", authResult.getPrincipal());
        log.info("로그인 성공 및 세션 생성");
        response.setStatus(HttpServletResponse.SC_OK);  // 200 상태 코드 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");response.getWriter().write("{\"message\": \"로그인 성공\"}");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (failed instanceof UsernameNotFoundException) {
            response.getWriter().write("{\"message\": \"아이디가 존재하지 않습니다.\"}");
        } else if (failed instanceof BadCredentialsException) {
            log.error(failed.getMessage());
            response.getWriter().write("{\"message\": \"비밀번호가 잘못되었습니다.\"}");
        } else {
            response.getWriter().write("{\"message\": \"로그인 실패\"}");
            failed.printStackTrace();
        }
    }
}
