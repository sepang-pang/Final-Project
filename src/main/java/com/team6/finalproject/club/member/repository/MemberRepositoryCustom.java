package com.team6.finalproject.club.member.repository;

import com.team6.finalproject.club.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    public Optional<Member> findActiveUser(Long userId);
    public Optional<Member> findActiveUserAndClub(Long clubId, Long userId);
    public List<Member> findActiveMembers(Long clubId);
}
