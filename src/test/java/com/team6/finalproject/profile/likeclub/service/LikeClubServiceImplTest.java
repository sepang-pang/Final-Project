package com.team6.finalproject.profile.likeclub.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.likeclub.entity.LikeClub;
import com.team6.finalproject.profile.likeclub.repository.LikeClubRepository;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.team6.finalproject.user.entity.UserRoleEnum.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeClubServiceImplTest {
    @InjectMocks
    private LikeClubServiceImpl likeClubService;
    @Mock
    private LikeClubRepository likeClubRepository;
    @Mock
    private ClubService clubService;
    @Mock
    private ProfileService profileService;

    private User user;
    private UserDetailsImpl userDetails;
    private Profile profile;
    @BeforeEach
    void setup() {
        // 유저 생성(Setter 지우고 builder 사용 시 변경)
        user = new User("유저네임", "비밀번호", "이메일", "2020-02-02", USER);
        user.setId(1L);
        userDetails = new UserDetailsImpl(user);
        //프로필 생성
        profile = Profile.builder()
                .nickname("닉네임")
                .introduction("소개글")
                .locate("서울시")
                .user(user)
                .build();
    }

    @Test
    @DisplayName("관심 동호회 등록 테스트")
    void addLikeClubTest() throws NotExistResourceException {
        // given
        // 소주제 생성
        InterestMinor interestMinor = InterestMinor.builder()
                .id(1L)
                .minorName("축구")
                .build();
        // 동호회 생성
        Club club = Club.builder()
                .name("동호회 이름")
                .description("동호회 소개")
                .maxMember(30)
                .activityType(ActivityTypeEnum.ONLINE)
                .joinType(JoinTypeEnum.IMMEDIATE)
                .minor(interestMinor)
                .isTrialAvailable(true)
                .build();
        // 관심 동호회 생성
        LikeClub likeClub = LikeClub.builder()
                .profile(profile)
                .club(club)
                .build();
        // 관심 동호회 요청
        LikeClubRequestDto requestDto = new LikeClubRequestDto();
        requestDto.setClubId(1L);

        when(profileService.findProfileByUserId(user.getId())).thenReturn(profile);
        when(clubService.findClub(requestDto.getClubId())).thenReturn(club);

        //when
        likeClubService.addLikeClub(requestDto, user);

        //then
        verify(profileService, times(1)).findProfileByUserId(user.getId());
        verify(clubService, times(1)).findClub(requestDto.getClubId());
        verify(likeClubRepository, times(1)).save(any(LikeClub.class));
    }
}