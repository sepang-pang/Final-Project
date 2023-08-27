package com.team6.finalproject.club.service;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface ClubService {

    //동호회 개설
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto, User user);

    // 동호회 폐쇄
    public ResponseEntity<ApiResponseDto> deleteClub(Long clubId, User user);

    public Club findClub(Long clubId);

    // 동호회 가입 신청
    public ResponseEntity<ApiResponseDto> joinClub(Long clubId, User user);

    // 동호회 가입 신청 승인 및 거절
    public ResponseEntity<ApiResponseDto> processClubApproval(Long applyId, User user, ApprovalStateEnum approvalState);
}

