package com.team6.finalproject.club.member.controller;

import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Secured(ClubRoleEnum.ClubRole.PRESIDENT)
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PutMapping("/members/{memberId}/staff") // 스태프 권한 부여
    public ResponseEntity<ApiResponseDto> grantRoleSTAFF(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.grantRole(memberId, userDetails.getUser(), ClubRoleEnum.STAFF);
    }

    @PutMapping("/members/{memberId}/normal") // 일반회원 권한 부여
    public ResponseEntity<ApiResponseDto> grantRoleNORMAL(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.grantRole(memberId, userDetails.getUser(), ClubRoleEnum.NORMAL);
    }
}
