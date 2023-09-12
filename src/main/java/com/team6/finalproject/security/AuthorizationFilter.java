package com.team6.finalproject.security;

import com.team6.finalproject.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j(topic = "authorizationFilter 입니다.")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("authorizationFilter 필터");
        UserDetails user = (UserDetailsImpl) request.getSession().getAttribute("username");

        String uri = request.getRequestURI();
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpeg")|| uri.endsWith(".jpg")|| uri.endsWith(".dmp")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (user != null) { // 조건 변경 해야함
            try {
                log.info(request.getRequestURI() + " 인증 정보 있음");
                setAuthentication(user.getUsername());
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (request.getRequestURI().equals("/api/login")) {
            log.info("로그인 시도입니다");
            log.info(request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().equals("/oauthsignup")) {
            log.info(request.getRequestURI());
            log.info("로그인 시도입니다");

            filterChain.doFilter(request, response);
            return;
        }

        if (user == null) {
            log.info("인증 정보가 없습니다. 로그인페이지로 이동합니다.");
            log.info(request.getRequestURI());
            response.sendRedirect("/login");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/signup", "/login", "/user/reissue", "/aa", "/kakao/callback",
                "/api/sms", "/api/findid", "/api/findpassword", "/api/users/id-inquiry"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
