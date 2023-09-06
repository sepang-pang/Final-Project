package com.team6.finalproject.club.service;

import com.team6.finalproject.advice.custom.*;
import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.ReadInterestMajorDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;
import java.util.List;


public interface ClubService {

    //동호회 개설
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto, User user) throws NotExistResourceException, DuplicateNameException, InvalidAgeRangeException;

    // 동호회 폐쇄
    public ResponseEntity<ApiResponseDto> deleteClub(Long clubId, User user) throws NotExistResourceException, AccessDeniedException;

    public Club findClub(Long clubId) throws NotExistResourceException;

    // 동호회 가입 신청
    public ResponseEntity<ApiResponseDto> joinClub(Long clubId, User user) throws DuplicateActionException, CapacityFullException, NotExistResourceException;

    // 동호회 가입 신청 승인 및 거절
    public ResponseEntity<ApiResponseDto> processClubApproval(Long applyId, User user, ApprovalStateEnum approvalState) throws AccessDeniedException, NotExistResourceException;

    // 동호회 멤버 전체 조회
    public List<MemberInquiryDto> readClubMembers(Long clubId) throws NotExistResourceException;

    // 특정 멤버 조회
    public MemberInquiryDto readClubMember(Long clubId, Long userId) throws NotExistResourceException;

    // 동호회 대주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMajor(Long majorId) throws NotExistResourceException;

    // 동호회 소주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMinor(Long minorId) throws NotExistResourceException;

    // 유저 관심사 별 동호회 조회
    List<ReadInterestMajorDto> clubsByUserInterest(User user) throws NotExistResourceException;

    // 유저 나이대별 동호회 조회
    List<ReadInterestMajorDto> clubsByUserAge(User user) throws NotExistResourceException;
}

