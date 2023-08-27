package com.team6.finalproject.club.member.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.club.member.repository.MemberRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // Member Save
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    // 동호회 가입 여부
    public Boolean existJoinClub(User user, Club club) {
        return memberRepository.findActiveUserAndClub(user, club).isPresent();
    }

    public Member findMember(User user, Club club) {
        return memberRepository.findActiveUserAndClub(user, club)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
