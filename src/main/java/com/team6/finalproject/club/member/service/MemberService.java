package com.team6.finalproject.club.member.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;
import java.util.List;


public interface MemberService {
    // 멤버 권한 부여
    public ResponseEntity<ApiResponseDto> grantRole(Long memberId, User user, ClubRoleEnum role) throws AccessDeniedException, NotExistResourceException;

    // 멤버 저장
    public void saveMember(Member member);

    // 동호회 가입 여부
    public Boolean existJoinClub(Long userId, Long clubId);

    // 멤버(복수) 조회
    public List<Member> findMembers(Long clubId);

    // 특정 멤버(단일) 조회
    public Member findMember(Long clubId, Long userId) throws NotExistResourceException;
}
