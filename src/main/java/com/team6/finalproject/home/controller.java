package com.team6.finalproject.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/api/oauthsignup")
    public String osignup() {
        return "oAuthSignup";
    }
}
