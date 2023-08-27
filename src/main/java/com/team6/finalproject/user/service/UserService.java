package com.team6.finalproject.user.service;

import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.dto.SignupRequestDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClubService clubService;
    private final ProfileService profileService;
    private final MemberService memberService;
    public void signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByUsername(signupRequestDto.getUserName()).isPresent()){
            throw new IllegalArgumentException("중복된 이름입니다.");
        }
        String loginId = signupRequestDto.getUserName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String email = signupRequestDto.getEmail();
        String birth = signupRequestDto.getBirth();
        UserRoleEnum role = signupRequestDto.getRole();

        userRepository.save(new User(loginId,password,email,birth,role));
    }
}
