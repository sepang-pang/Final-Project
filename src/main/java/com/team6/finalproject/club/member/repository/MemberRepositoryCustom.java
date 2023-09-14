package com.team6.finalproject.club.member.repository;

import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    public Optional<Member> findActiveUser(Long userId);
    public Optional<Member> findActiveUserAndClub(Long userId, Long clubId);
    public List<Member> findActiveMembers(Long clubId);
    List<Member> findJoinClubs(User user); // 가입한 동호회 조회
}
