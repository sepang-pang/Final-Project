package com.team6.finalproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "authorizationFilter 입니다.")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserDetails user = (UserDetailsImpl) request.getSession().getAttribute("username");
        String requestURI = request.getRequestURI();
        log.info("Current request URI: " + requestURI);
        if (isPermitUrl(requestURI) || isStaticResource(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("authorizationFilter 필터 4");

        if (user != null) {
            try {
                log.info(request.getRequestURI() + " 인증 정보 있음");
                setAuthentication(user.getUsername());
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else { // user is null, which means the user is not authenticated
            if (request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/oauthsignup")) {
                log.info("로그인 시도입니다");
                log.info(request.getRequestURI());
                filterChain.doFilter(request, response);
            } else {
                log.info("인증 정보가 없습니다. 로그인페이지로 이동합니다.");
                log.info(request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"로그인이 필요한 페이지입니다.\"}");
            }
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String[] excludePath = {
                "/", "/signup", "/login", "/user/reissue", "/aa", "/kakao/callback", "/api/sms/send", "/api/sms/check",
                "/sub-main", "/main", "/api/clubs/get/recent", "/api/clubs/get/popularity",
                "/api/clubs/get/interest-minor/{minorId}", "/api/findid", "/api/findpassword",
                "/api/users/id-inquiry", "/api/users/pw-inquiry", "/api/users/pw-auth", "/api/users/id-auth"
        };

        String path = request.getRequestURI();

        for (String pattern : excludePath) {
            if (pathMatcher.match(pattern, path)) {
                return true; // 요청 경로가 제외 경로 중 하나와 일치하면 필터링하지 않음
            }
        }

        return false; // 제외 경로와 일치하지 않는 경우 필터링 수행
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

    private boolean isPermitUrl(String requestURI) {
        return requestURI.endsWith("/api/**") || requestURI.endsWith("/login") || requestURI.endsWith("/signup") || requestURI.endsWith("/main");
    }

    private boolean isStaticResource(String requestURI) {
        return requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".html");
    }
}
