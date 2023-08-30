package com.team6.finalproject.club.service;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.ReadInterestMajorDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;


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

    // 동호회 멤버 전체 조회
    public List<MemberInquiryDto> readClubMembers(Long clubId);

    // 특정 멤버 조회
    public MemberInquiryDto readClubMember(Long clubId, Long userId);

    // 동호회 대주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMajor(Long majorId);

    // 동호회 소주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMinor(Long minorId);
}

