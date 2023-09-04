package com.team6.finalproject.user.service;

import com.team6.finalproject.user.dto.SignupRequestDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("중복된 이름입니다.");
        }
        String loginId = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String email = signupRequestDto.getEmail();
        String birth = signupRequestDto.getBirth();
        UserRoleEnum role = signupRequestDto.getRole();

        userRepository.save(new User(loginId,password,email,birth,role));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
