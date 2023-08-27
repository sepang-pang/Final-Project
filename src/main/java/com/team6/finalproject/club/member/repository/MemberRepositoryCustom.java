package com.team6.finalproject.club.member.repository;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.user.entity.User;

import java.util.Optional;

public interface MemberRepositoryCustom {
    public Optional<Member> findActiveUserAndClub(Long userId, Long clubId);
}
