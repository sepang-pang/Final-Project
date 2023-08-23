package com.team6.finalproject.user.controller;

import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.dto.SignupRequestDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/aa")
    public String  aa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getUsername());
        return "main";
    }
}
