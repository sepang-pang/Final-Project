package com.team6.finalproject.club.controller;

import com.team6.finalproject.advice.custom.*;
import com.team6.finalproject.club.apply.dto.ClubAppliesResponseDto;
import com.team6.finalproject.club.apply.service.ApplyJoinClubService;
import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.ReadInterestMajorDto;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.profile.likeclub.service.LikeClubService;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j(topic = "controller 로직")
public class ClubController {

    private final ClubService clubService;
    private final LikeClubService likeClubService;
    private final MemberService memberService;
    private final ProfileService profileService;
    private final ApplyJoinClubService applyJoinClubService;

    @GetMapping("/open-club") // 동호회 개설
    public String openClub(@AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        if (profileService.existValidProfile(userDetails.getUser().getId())) {
            return "openClub";
        } else {
            throw new NotExistResourceException("프로필을 먼저 작성해주세요.");
        }
    }

    @GetMapping("/club-update/{clubId}") // 동호회 전체 조회
    public String updateClubPage(@PathVariable("clubId") Long clubId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        model.addAttribute("clubId", clubId);
        model.addAttribute("currentUsername", userDetails.getUsername());
        model.addAttribute("clubUsername", clubService.findClub(clubId).getUsername());
        return "club-update";
    }

    @GetMapping("/club-detail/{id}") // 동호회 상세 조회
    public String clubPage(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        model.addAttribute("clubId", id);
        model.addAttribute("currentUsername", userDetails.getUsername());
        model.addAttribute("clubUsername", clubService.findClub(id).getUsername());
        model.addAttribute("likeStatus", likeClubService.isLikeClub(id, userDetails.getUser()));
        model.addAttribute("memberStatus", memberService.existJoinClub(userDetails.getUser().getId(), id));
        model.addAttribute("applyStatus", applyJoinClubService.hasPendingApplication(userDetails.getUser().getId(), id));
        return "club-detail";
    }

    @GetMapping("/my-club") // 개설한 동호회 목록 조회
    public String myClubs(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ClubResponseDto> clubs = clubService.myClubs(userDetails.getUser());
        model.addAttribute("clubs", clubs);
        model.addAttribute("pageTitle", "개설한 동호회");
        return "my-club";
    }

    @GetMapping("/my-join-club") // 가입한 동호회 목록 조회
    public String myJoinClubs(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ClubResponseDto> clubs = clubService.myJoinClubs(userDetails.getUser());
        model.addAttribute("clubs", clubs);
        model.addAttribute("pageTitle", "가입한 동호회");
        return "my-club";
    }

    @GetMapping("/my-like-club") // 찜한 동호회 목록 조회
    public String myLikeClubs(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ClubResponseDto> clubs = clubService.myLikeClubs(userDetails.getUser());
        model.addAttribute("clubs", clubs);
        model.addAttribute("pageTitle", "찜한 동호회");
        return "my-club";
    }


    @PostMapping("/clubs") // 동호회 개설
    @ResponseBody
    public ResponseEntity createClub(@RequestPart ClubRequestDto clubRequestDto, @RequestPart MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, DuplicateNameException, InvalidAgeRangeException, IOException {
        ClubResponseDto clubResponseDto = clubService.createClub(clubRequestDto, file, userDetails.getUser());
        return ResponseEntity.ok().body(clubResponseDto);
    }

    @PutMapping("/api/clubs/{clubId}") // 동호회 수정
    public ClubResponseDto updateClub(@PathVariable Long clubId, @RequestPart ClubRequestDto clubRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart MultipartFile multipartFile) throws NotExistResourceException, DuplicateNameException, InvalidAgeRangeException, IOException {
        return clubService.updateClub(clubId, clubRequestDto, userDetails.getUser(), multipartFile);
    }

    //    @ResponseBody
    @DeleteMapping("/clubs/{clubId}") // 동호회 폐쇄
    @ResponseBody
    public ResponseEntity<ApiResponseDto> deleteClub(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, AccessDeniedException {
        return clubService.deleteClub(clubId, userDetails.getUser());
    }

    @PutMapping("/clubs/{clubId}/apply") // 동호회 가입 신청
    public ResponseEntity<ApiResponseDto> applyJoinClub(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws DuplicateActionException, NotExistResourceException, CapacityFullException {
        return clubService.joinClub(clubId, userDetails.getUser());
    }

    @GetMapping("/clubs/{clubId}/applies") // 동호회 가입 신청 조회
    @ResponseBody
    public List<ClubAppliesResponseDto> readClubApplies(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException {
        return clubService.readClubApplies(clubId, userDetails.getUser());
    }

    @PutMapping("/clubs/applies/{applyId}/approve") // 동호회 가입 승인
    @Secured(ClubRoleEnum.ClubRole.PRESIDENT)
    public ResponseEntity<ApiResponseDto> approveJoinRequest(@PathVariable Long applyId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, AccessDeniedException {
        return clubService.processClubApproval(applyId, userDetails.getUser(), ApprovalStateEnum.APPROVE);
    }

    @PutMapping("/clubs/applies/{applyId}/refuse") // 동호회 가입 거절
    @Secured(ClubRoleEnum.ClubRole.PRESIDENT)
    public ResponseEntity<ApiResponseDto> refuseJoinRequest(@PathVariable Long applyId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotExistResourceException, AccessDeniedException {
        return clubService.processClubApproval(applyId, userDetails.getUser(), ApprovalStateEnum.REFUSE);
    }

    @GetMapping("/clubs/{clubId}/members") // 동호회 멤버 전체 조회
    @ResponseBody
    public List<MemberInquiryDto> readClubMembers(@PathVariable Long clubId) throws NotExistResourceException {
        return clubService.readClubMembers(clubId);
    }

    @GetMapping("clubs/{clubId}/user/{userId}") // 특정 멤버 조회
    public MemberInquiryDto readClubMember(@PathVariable Long clubId, @PathVariable Long userId) throws NotExistResourceException {
        return clubService.readClubMember(clubId, userId);
    }

    @GetMapping("/clubs/{clubId}") // 동호회 상세 조회
    @ResponseBody
    public ClubResponseDto readClub(@PathVariable Long clubId) throws NotExistResourceException {
        return clubService.readClub(clubId);
    }

    @GetMapping("clubs/interest-major/{majorId}") // 동호회 대주제 별 조회
    public List<ReadInterestMajorDto> readSelectInterestMajor(@PathVariable Long majorId) throws NotExistResourceException {
        return clubService.readSelectInterestMajor(majorId);
    }

    @GetMapping("clubs/get/interest-minor/{minorId}") // 동호회 소주제 별 조회
    @ResponseBody
    public List<ClubResponseDto> readSelectInterestMinor(@PathVariable Long minorId) throws NotExistResourceException {
        return clubService.readSelectInterestMinor(minorId);
    }

    @GetMapping("/clubs/get/recent") // 최근 개설된 동호회 추천
    @ResponseBody
    public List<ClubResponseDto> clubsByRecent() {
        return clubService.clubsByRecent();
    }

    @GetMapping("/clubs/get/popularity") // 인기 급상승 동호회 추천
    @ResponseBody
    public List<ClubResponseDto> clubsByPopularity() throws NotExistResourceException {
        return clubService.clubsByPopularity();
    }

    @GetMapping("/clubs/recommend") // 유저에게 최적합 동호회 추천
    @ResponseBody
    public List<ClubResponseDto> recommendClubs(@RequestParam("radius") double radius, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return clubService.findRecommendedClubsForUser(radius, userDetails.getUser());
    }

}
