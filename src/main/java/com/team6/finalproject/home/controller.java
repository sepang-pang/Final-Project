package com.team6.finalproject.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class controller {

    @GetMapping("/signup")
    public String home() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/main")
    public String main() {

        return "main";
    }

    @GetMapping("/")
    public String defaultRedirect() {
        return "sub-main";
    }

    @GetMapping("/sub-main")
    public String subMain() {
        return "sub-main";
    }


    @GetMapping("/api/isAuthenticated")  // 로그인 여부 확인
    public ResponseEntity<Map<String, Boolean>> isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

        Map<String, Boolean> response = new HashMap<>();
        response.put("isAuthenticated", isAuthenticated);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/oauth-signup")
    public String osignup() {
        return "oauth-signup";
    }

    @GetMapping("/api/find-id")
    public String findId() {
        return "find-id";
    }

    @GetMapping("/api/find-password")
    public String findPassword() {
        return "find-password";
    }

    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "withdrawal";
    }

    @GetMapping("/api/reset-password")
    public String resetPassword() {
        return "reset-password";
    }
}
