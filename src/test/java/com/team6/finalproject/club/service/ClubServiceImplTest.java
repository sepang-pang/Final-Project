package com.team6.finalproject.club.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.apply.service.ApplyJoinClubService;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.club.member.dto.MemberInquiryDto;
import com.team6.finalproject.club.member.entity.Member;
import com.team6.finalproject.club.member.service.MemberService;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClubServiceImplTest {

    @Mock
    private ClubRepository clubRepository;
    @Mock
    private InterestMinorService interestMinorService;
    @Mock
    private ProfileService profileService;
    @Mock
    private MemberService memberService;
    @Mock
    private ApplyJoinClubService applyJoinClubService;
    @InjectMocks
    private ClubServiceImpl clubService;

    @Nested
    @DisplayName("동호회 멤버 전체 조회") // 구문 커버리지 3/3 100%
    class Test1 {
        @Test
        @DisplayName("멤버 정상 반환 테스트 : 구문 1")
        public void test1() {
            // given
            Long givenClubId = 1L;

            Member mockMember = mock(Member.class);
            List<Member> expectedMembers = Collections.singletonList(mockMember);

            when(memberService.findMembers(givenClubId)).thenReturn(expectedMembers);

            // when
            List<Member> resultMembers = memberService.findMembers(givenClubId);

            // then
            assertEquals(expectedMembers, resultMembers); // 리스트를 직접 비교
        }

        @Test
        @DisplayName("멤버 조회 실패 테스트 : 구문 2")
        public void test2() {
            // given
            Long givenClubId = 1L;
            when(memberService.findMembers(givenClubId)).thenReturn(Collections.emptyList()); // 빈 리스트 반환

            // when ~ then
            assertThrows(NotExistResourceException.class, () -> {
                clubService.readClubMembers(givenClubId);
            }, "존재하지 않는 회원입니다.");
        }

        @Test
        @DisplayName("멤버 조회 성공 테스트 : 구문 3")
        public void test3() throws NotExistResourceException {
            // given
            Long givenClubId = 1L;

            User mockUser = mock(User.class);
            Profile mockProfile = mock(Profile.class);
            when(mockUser.getProfile()).thenReturn(mockProfile);
            when(mockProfile.getNickname()).thenReturn("TestNickName");
            when(mockUser.getBirth()).thenReturn("1997-04-11");
            when(mockProfile.getIntroduction()).thenReturn("Test Introduction");

            Member mockMember = mock(Member.class);
            when(mockMember.getUser()).thenReturn(mockUser);

            List<Member> mockMembers = Collections.singletonList(mockMember);
            when(memberService.findMembers(givenClubId)).thenReturn(mockMembers);

            // when
            List<MemberInquiryDto> result = clubService.readClubMembers(givenClubId);

            // then
            assertEquals(1, result.size());

            MemberInquiryDto dto = result.get(0);
            assertEquals("TestNickName", dto.getNickName());
            assertEquals("1997-04-11", dto.getBirth());
            assertEquals("Test Introduction", dto.getIntroduction());
        }
    }
}