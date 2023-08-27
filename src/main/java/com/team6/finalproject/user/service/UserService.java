package com.team6.finalproject.user.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.repository.ProfileRepository;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.dto.SignupRequestDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import com.team6.finalproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ApiResponseDto> joinClub(Long id, User user) {
        Club club = clubService.findClub(id);

        // 거주지 입력 여부 판단
        if(profileService.existValidLocate(user.getId())){
            throw new IllegalArgumentException("거주지를 입력해주세요");
        }

        // 관심사 등록 여부 판단
        if(profileService.existValidInterest(user.getId())) {
            throw new IllegalArgumentException("관심사를 등록해주세요");
        }

        // 이미 가입한 동호회 예외 처리
        if(memberService.existJoinClub(user, club)) {
            throw new IllegalArgumentException("이미 가입한 동호회입니다.");
        }

        Member member = Member.builder()
                .user(user)
                .club(club)
                .build();

        memberService.saveMember(member);
        return ResponseEntity.ok().body(new ApiResponseDto("동호회 가입 성공", 200));
    }
}
