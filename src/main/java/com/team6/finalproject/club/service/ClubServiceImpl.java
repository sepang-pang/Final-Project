package com.team6.finalproject.club.service;

import com.team6.finalproject.club.apply.entity.ApplyJoinClub;
import com.team6.finalproject.club.apply.service.ApplyJoinClubService;
import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.dto.InterestMajorDto;
import com.team6.finalproject.club.dto.InterestMinorDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.profile.dto.ProfileNickNameDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "club service 로직")
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final InterestMinorService interestMinorService;
    private final ProfileService profileService;
    private final MemberService memberService;
    private final ApplyJoinClubService applyJoinClubService;

    // 동호회 멤버 조회
    // 멤버 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<MemberInquiryDto> getClubMember(Long clubId) {
        // 클럽에 속한 멤버 조회
        List<Member> members = memberService.findMembers(clubId);

        // 반환
        // 성별 및 이미지도 함께 반환 예정
        return members.stream()
                .map(member -> MemberInquiryDto.builder()
                        .nickName(member.getUser().getProfile().getNickname())
                        .birth(member.getUser().getBirth())
                        .introduction(member.getUser().getProfile().getIntroduction())
                        .build())
                .toList();
    }

    // 동호회 개설
    @Override
    @Transactional
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto, User user) {

        // 유저 프로필 조회
        Profile profile = profileService.findProfileByUserId(user.getId());

        // 소주제 존재 유무 확인
        InterestMinor interestMinor = interestMinorService.existsInterestMinor(clubRequestDto.getMinorId());

        // 동호회 이름 존재 확인
        if (clubRepository.findByActiveClubName(clubRequestDto.getName()).isPresent()) { // isPresent(): 존재하면 true, 존재하지 않으면 false
            throw new IllegalArgumentException("동호회 이름이 이미 존재합니다.");
        }

        // 가입 방식 설정
        log.info("가입 방식 설정");
        JoinTypeEnum join = JoinTypeEnum.APPROVAL;
        if (clubRequestDto.isOpenJoinType()) {
            join = JoinTypeEnum.IMMEDIATE;
        }

        // 활동 방식 설정
        log.info("활동 방식 설정");
        ActivityTypeEnum activity = ActivityTypeEnum.OFFLINE;
        if (clubRequestDto.isOnline()) {
            activity = ActivityTypeEnum.ONLINE;
        }

        // 동호회 개설
        log.info("동호회 개설");
        Club club = Club.builder()
                .username(user.getUsername())
                .nickName(new ProfileNickNameDto(profile.getNickname()).getNickName())
                .name(clubRequestDto.getName())
                .description(clubRequestDto.getDescription())
                .maxMember(clubRequestDto.getMaxMember())
                .activityType(activity)
                .joinType(join)
                .minor(interestMinor)
                .isTrialAvailable(clubRequestDto.isTrialAvailable())
                .build();

        // DB 저장
        log.info("DB 저장");
        clubRepository.save(club);

        // 동호회 멤버 테이블에 추가 -> 동호회 회장 권한 획득
        log.info("멤버 저장");
        Member member = Member.builder()
                .user(user)
                .club(club)
                .clubRoleEnum(ClubRoleEnum.PRESIDENT)
                .build();
        memberService.saveMember(member);

        // response 반환
        log.info("response 반환");
        return new ClubResponseDto(
                user,
                club,
                new InterestMajorDto(club.getMinor().getInterestMajor()),
                new InterestMinorDto(club.getMinor()));
    }

    // 동호회 폐쇄
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteClub(Long clubId, User user) {
        //  동호회 존재 여부 확인
        // QueryDsl 로 삭제된 동호회는 조회 x
        Club targetClub = clubRepository.findByActiveId(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동호회입니다."));

        // Soft - Delete 메서드
        // 동호회 개설자가 아니면 삭제 불가
        // 동호회 멤버도 delete 하기
        if (!targetClub.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        targetClub.deleteClub();

        // 반환
        return ResponseEntity.ok().body(new ApiResponseDto("동호회 삭제 성공", 200));
    }

    // 동호회 가입 신청
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> joinClub(Long clubId, User user) {
        // 가입 대상 동호회 조회
        Club targetClub = findClub(clubId);

//        // 거주지 입력 여부 판단
//        if(profileService.existValidLocate(user.getId())){
//            throw new IllegalArgumentException("거주지를 입력해주세요");
//        }
//
//        // 관심사 등록 여부 판단
//        if(profileService.existValidInterest(user.getId())) {
//            throw new IllegalArgumentException("관심사를 등록해주세요");
//        }

        // ======== 즉시 가입 동호회 ======== //
        if (targetClub.getJoinType() == (JoinTypeEnum.IMMEDIATE)) {
            Member member = Member.builder()
                    .user(user)
                    .club(targetClub)
                    .build();
            memberService.saveMember(member);
            return ResponseEntity.ok().body(new ApiResponseDto("동호회 가입 성공", 200));
        }


        // ======== 가입 승인 동호회 ======== //
        // 가입여부 확인
        if (memberService.existJoinClub(user.getId(), targetClub.getId())) {
            throw new IllegalArgumentException("이미 소속된 동호회입니다.");
        }

        // 신청여부 확인
        if (applyJoinClubService.hasPendingApplication(user.getId(), clubId)) {
            throw new IllegalArgumentException("이미 가입 신청한 상태입니다.");
        }

        // 가입 신청서 제출
        ApplyJoinClub application = ApplyJoinClub.builder()
                .approvalStateEnum(ApprovalStateEnum.PENDING)
                .user(user)
                .club(targetClub)
                .build();
        applyJoinClubService.saveApplyJoinClub(application);
        return ResponseEntity.ok().body(new ApiResponseDto("동호회 가입 신청 성공", 200));
    }

    // 동호회 가입 승인
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> processClubApproval(Long applyId, User user, ApprovalStateEnum approvalState) {
        ApplyJoinClub applyJoinClub = applyJoinClubService.findApplication(applyId); // 신청서 조회
        Club club = findClub(applyJoinClub.getClub().getId()); // 신청한 동호회 조회

        if (!applyJoinClub.getClub().getUsername().equals(user.getUsername())) { // 신청한 동호회의 개설자 이름과 현재 인가된 유저의 이름 비교
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        applyJoinClub.updateApprovalState(approvalState); // 신청서의 상태 설정

        if (approvalState == ApprovalStateEnum.APPROVE) { // 상태값이 APPROVE 면 가입 승인
            Member member = Member.builder()
                    .user(applyJoinClub.getUser())
                    .club(club)
                    .build();
            memberService.saveMember(member);
            return ResponseEntity.ok().body(new ApiResponseDto("동호회 가입 승인", 200));

        } else { // APPROVE 가 아니라면 가입 거절
            return ResponseEntity.ok().body(new ApiResponseDto("동호회 가입 거절", 200));
        }
    }


    // ================= 조회 메서드 ================= //

    // 동호회 조회 메서드
    @Override
    public Club findClub(Long id) {
        return clubRepository.findByActiveId(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동호회입니다."));
    }
}
