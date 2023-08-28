package com.team6.finalproject.club.member.repository;

import com.team6.finalproject.club.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {
    public Optional<Member> findActiveUser(Long userId);
    public Optional<Member> findActiveUserAndClub(Long userId, Long clubId);
}
