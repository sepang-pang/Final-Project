package com.team6.finalproject.user.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.redis.RedisUtil;
import com.team6.finalproject.user.dto.*;
import com.team6.finalproject.user.email.EmailAuth;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuth emailAuth;
    private final RedisUtil redisUtil;

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

    @Override
    @Transactional(readOnly = true)
    public void idInquiry(EmailRequestDto requestDto) throws NotExistResourceException, MessagingException {
        String email = requestDto.getEmail();

        // email 존재 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isEmpty()) {
            throw new NotExistResourceException("존재하지 않는 Email 입니다.");
        }

        emailAuth.sendCode(email); // 인증코드 메일 발송
    }

    @Override
    public boolean verifyCode(AuthRequestDto requestDto) throws NotExistResourceException {
        String email = requestDto.getEmail();

        // email Key로 인증코드 가져오기
        String codeFindByEmail = redisUtil.getData(email);
        if (codeFindByEmail == null) {
            throw new NotExistResourceException("인증코드를 찾을 수 없습니다.");
        }

        String code = requestDto.getCode();
        return codeFindByEmail.equals(code);
    }

    @Override
    @Transactional(readOnly = true)
    public IdResponseDto returnId(String email) throws NotExistResourceException {
        User user = findByEmail(email);

        String username = user.getUsername();
        return new IdResponseDto(username);
    }

    @Override
    @Transactional(readOnly = true)
    public void passwordInquiry(PasswordInquiryDto inquiryDto) throws NotExistResourceException, MessagingException {
        String email = inquiryDto.getEmail();
        User user = findByEmail(email);

        String username = inquiryDto.getUsername();
        if (!username.equals(user.getUsername())) { // 요청한 계정의 ID와 Email 일치 여부 확인
            throw new IllegalArgumentException("입력한 계정 정보가 일치하지 않습니다.");
        }

        emailAuth.sendCode(email); // 인증코드 메일 발송
    }

    @Override
    @Transactional
    public void returnPassword(String email) throws MessagingException, NotExistResourceException {
        String password = emailAuth.sendPassword(email); // 발송한 임시 비밀번호
        String encodedPassword = passwordEncoder.encode(password);

        User user = findByEmail(email);
        user.updatePassword(encodedPassword); // 임시 비밀번호로 변경
    }

    private User findByEmail(String email) throws NotExistResourceException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotExistResourceException("Email을 찾을 수 없습니다."));
    }
}
