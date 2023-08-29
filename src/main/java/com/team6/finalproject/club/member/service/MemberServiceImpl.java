package com.team6.finalproject.club.member.service;

import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.club.member.repository.MemberRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 멤버 권한 부여
    @Override
    public ResponseEntity<ApiResponseDto> grantRole(Long memberId, User user, ClubRoleEnum role) {
        // 동호회에서 권한을 부여하고자 하는 회원이 존재하는지 확인
        Member member = findMember(memberId);

        // 조회된 동호회 개설자와 현재 인가된 유저의 이름이 같은지 확인함으로써 권한 확인
        if (member.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        // 권한 부여
        member.grantRole(role);

        return ResponseEntity.ok().body(new ApiResponseDto("권한 부여 성공", 200));
    }

    // 멤버 저장
    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    // 동호회 가입 여부
    @Override
    public Boolean existJoinClub(Long userId, Long clubId) {
        return memberRepository.findActiveUserAndClub(userId, clubId).isPresent();
    }

    @Override
    public List<Member> findMembers(Long clubId) {
        return memberRepository.findActiveMembers(clubId);
    }

    // 멤버 조회
    public Member findMember(Long userId) {
        return memberRepository.findActiveUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
