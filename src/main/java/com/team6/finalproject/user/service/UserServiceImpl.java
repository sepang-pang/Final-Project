package com.team6.finalproject.user.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.common.redis.RedisUtil;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.dto.*;
import com.team6.finalproject.common.email.EmailAuth;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuth emailAuth;
    private final RedisUtil redisUtil;

    // 회원가입
    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("중복된 이름입니다.");
        }

        if(!redisUtil.isVerified(signupRequestDto.getPhoneNumber())){
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .phone(signupRequestDto.getPhoneNumber())
                .email(signupRequestDto.getEmail())
                .birth(signupRequestDto.getBirth())
                .age(signupRequestDto.getAge())
                .role(role)
                .build();

        userRepository.save(user);
    }

    // 유저저장
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean findPhoneNumber(String phoneNumber) {
        return userRepository.findByActivePhone(phoneNumber).isPresent();
    }

    // ID 찾기
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

    // 인증코드 확인
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

    // 인증 후 ID 반환
    @Override
    @Transactional(readOnly = true)
    public IdResponseDto returnId(String email) throws NotExistResourceException {
        User user = findByEmail(email);

        String username = user.getUsername();
        return new IdResponseDto(username);
    }

    // 비밀번호 찾기
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

    // 비밀번호 재설정
    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDto passwordDto, User user) {
        String currentPassword = passwordDto.getCurrentPassword();
        String newPassword = passwordDto.getNewPassword();
        String checkPassword = passwordDto.getCheckPassword();

        // 두번 입력한 비밀번호 비교
        if (!newPassword.equals(checkPassword)) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다");
        }
        // 비밀번호에 ID 포함 불가
        if (newPassword.contains(user.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 ID를 포함할 수 없습니다.");
        }
        // 현재 비밀번호 불일치
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        // 현재 비밀번호로 변경 불가
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호로 변경할 수 없습니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDto resetPasswordDto) throws NotExistResourceException{
        User user = findByUsername(resetPasswordDto.getUsername());
        String newPassword = resetPasswordDto.getNewPassword();
        String checkPassword = resetPasswordDto.getCheckPassword();

        // 두번 입력한 비밀번호 비교
        if (!newPassword.equals(checkPassword)) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다");
        }
        // 비밀번호에 ID 포함 불가
        if (newPassword.contains(user.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 ID를 포함할 수 없습니다.");
        }
        // 현재 비밀번호로 변경 불가
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호로 변경할 수 없습니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
        userRepository.save(user);
    }

    //비밀번호 찾기.
    @Override
    @Transactional
    public void withdrawal(User user, WithdrawalRequestDto withdrawalRequestDto) {
        log.info(user.getUsername());

        log.info(withdrawalRequestDto.getPassword());
        if (!passwordEncoder.matches(withdrawalRequestDto.getPassword(),user.getPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다");
        }

        log.info("변경");
        user.deletedUser();
        log.info("변경 후");

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) throws NotExistResourceException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotExistResourceException("유저를 찾을 수 없습니다."));
    }

    @Override
    public User findByUser(Long id) throws NotExistResourceException {
        return userRepository.findByActiveId(id).orElseThrow(
                () -> new NotExistResourceException("유저를 찾을 수 없습니다."));
    }

    @Override
    public User findByUsername(String username) throws NotExistResourceException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotExistResourceException("유저를 찾을 수 없습니다."));
    }

}
