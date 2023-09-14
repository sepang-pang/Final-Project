package com.team6.finalproject.club.service;

import com.team6.finalproject.advice.custom.*;
import com.team6.finalproject.club.apply.dto.ClubAppliesResponseDto;
import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.ReadInterestMajorDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;


public interface ClubService {

    // 동호회 개설
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto, User user) throws NotExistResourceException, DuplicateNameException, InvalidAgeRangeException, IOException;

    // 동호회 수정
    public ClubResponseDto updateClub(Long clubId, ClubRequestDto clubRequestDto, User user, MultipartFile multipartFile) throws NotExistResourceException, DuplicateNameException, InvalidAgeRangeException, IOException;

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

    // 동호회 상세 조회
    public ClubResponseDto readClub(Long clubId) throws NotExistResourceException;

    // 동호회 대주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMajor(Long majorId) throws NotExistResourceException;

    // 동호회 소주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMinor(Long minorId) throws NotExistResourceException;

    // 유저 기준 거리순 동호회 조회
    List<ReadInterestMajorDto> clubsByUserDistance(User user)throws NotExistResourceException;

    // 유저 관심사 별 동호회 조회
    List<ReadInterestMajorDto> clubsByUserInterest(User user) throws NotExistResourceException;

    // 유저 나이대별 동호회 조회
    List<ReadInterestMajorDto> clubsByUserAge(User user) throws NotExistResourceException;

    // 최근 개설된 동호회 조회
    public List<ClubResponseDto> clubsByRecent();

    // 인기 급상승 동호회 조회
    public List<ClubResponseDto> clubsByPopularity() throws NotExistResourceException;

    // 거리, 연령대, 관심사가 모두 부합하는 동호회 조회
    public List<ClubResponseDto> findRecommendedClubsForUser(double radius, User user);
    public List<ClubAppliesResponseDto> readClubApplies(Long clubId, User user) throws NotExistResourceException;

    // 내가 개설한 동호회 조회
    List<ClubResponseDto> myClubs(User user);
    // 가입한 동호회 조회
    List<ClubResponseDto> myJoinClubs(User user);
}

