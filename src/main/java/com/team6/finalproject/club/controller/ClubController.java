package com.team6.finalproject.club.controller;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j(topic = "controller 로직")
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/clubs") // 동호회 개설
    public ClubResponseDto createClub(@RequestBody ClubRequestDto clubRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.createClub(clubRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/clubs/{clubId}") // 동호회 폐쇄
    public ResponseEntity<ApiResponseDto> deleteClub(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.deleteClub(clubId, userDetails.getUser());
    }

    @PutMapping("clubs/{clubId}/apply") // 동호회 가입 신청
    public ResponseEntity<ApiResponseDto> applyJoinClub(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.joinClub(clubId, userDetails.getUser());
    }

    @PutMapping("/clubs/applies/{applyId}/approve") // 동호회 가입 승인
    @Secured(ClubRoleEnum.ClubRole.PRESIDENT)
    public ResponseEntity<ApiResponseDto> approveJoinRequest(@PathVariable Long applyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.processClubApproval(applyId, userDetails.getUser(), ApprovalStateEnum.APPROVE);
    }

    @PutMapping("/clubs/applies/{applyId}/refuse") // 동호회 가입 거절
    @Secured(ClubRoleEnum.ClubRole.PRESIDENT)
    public ResponseEntity<ApiResponseDto> refuseJoinRequest(@PathVariable Long applyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.processClubApproval(applyId, userDetails.getUser(), ApprovalStateEnum.REFUSE);
    }

    @GetMapping("/clubs/{clubId}/members") // 동호회 멤버 전체 조회
    public List<MemberInquiryDto> readClubMembers(@PathVariable Long clubId) {
        return clubService.getClubMembers(clubId);
    }

    @GetMapping("clubs/{clubId}/user/{userId}") // 특정 멤버 조회
    public MemberInquiryDto readClubMember(@PathVariable Long clubId, @PathVariable Long userId) {
        return clubService.readClubMember(clubId, userId);
    }
}
