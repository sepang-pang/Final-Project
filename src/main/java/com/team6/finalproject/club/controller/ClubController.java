package com.team6.finalproject.club.controller;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j(topic = "controller 로직")
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/clubs")
    public ClubResponseDto createClub(@RequestBody ClubRequestDto clubRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.createClub(clubRequestDto, userDetails.getUser());
    }
    @DeleteMapping("/clubs/{clubId}")
    public ResponseEntity<ApiResponseDto> deleteClub(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.deleteClub(clubId, userDetails.getUser());
    }

    @PutMapping("clubs/{clubId}/apply")
    public ResponseEntity<ApiResponseDto> applyJoinClub(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.joinClub(clubId, userDetails.getUser());
    }

    @PutMapping("/clubs/{applyId}/approve")
    public ResponseEntity<ApiResponseDto> approveJoinRequest(@PathVariable Long applyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.processClubApproval(applyId, userDetails.getUser(), ApprovalStateEnum.APPROVE);
    }

    @PutMapping("/clubs/{applyId}/refuse")
    public ResponseEntity<ApiResponseDto> refuseJoinRequest(@PathVariable Long applyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.processClubApproval(applyId, userDetails.getUser(), ApprovalStateEnum.REFUSE);
    }
}
