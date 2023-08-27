package com.team6.finalproject.profile.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.profile.dto.InterestRequestDto;
import com.team6.finalproject.profile.dto.LikeClubRequestDto;
import com.team6.finalproject.profile.dto.ProfileRequestDto;
import com.team6.finalproject.profile.dto.ProfileResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.likeclub.entity.LikeClub;
import com.team6.finalproject.profile.likeclub.service.LikeClubService;
import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import com.team6.finalproject.profile.profileinterest.service.ProfileInterestService;
import com.team6.finalproject.profile.repository.ProfileRepository;
import com.team6.finalproject.security.UserDetailsImpl;
import com.team6.finalproject.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.team6.finalproject.user.entity.UserRoleEnum.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    @Mock
    private ProfileRepository profileRepository;
    @InjectMocks
    private ProfileServiceImpl profileService;
    @Mock
    private FileUploader fileUploader;
    @Mock
    private InterestMinorService interestMinorService;
    @Mock
    private ProfileInterestService profileInterestService;
    @Mock
    private ClubService clubService;
    @Mock
    private LikeClubService likeClubService;

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
    @DisplayName("프로필 생성 테스트")
    void createProfileTest() {
        // given
        // save() 호출 시 profile 반환
        when(profileRepository.save(profile)).thenReturn(profile);
        // findByUserId() 호출 시 profile 반환
        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));

        // when
        Profile savedProfile = profileRepository.save(profile);
        Profile findProfile = profileRepository.findByUserId(1L).orElseThrow();

        // then
        // 저장된 프로필과 userId 1번으로 찾아온 프로필 비교
        assertEquals(savedProfile, findProfile);
    }

    @Test
    @DisplayName("프로필 조회 테스트")
    void getProfileTest() {
        // given
        // findByUserId() 호출 시 profile 반환
        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));

        // when
        ProfileResponseDto response = profileService.getProfile(userDetails.getUser());

        // then
        assertEquals(profile.getNickname(), response.getNickname());
    }

    @Test
    @DisplayName("프로필 수정 테스트")
    void updateProfileTest() {
        // given
        // findByUserId() 호출 시 profile 반환
        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));

        // 수정할 닉네임 주입
        ProfileRequestDto requestDto = ProfileRequestDto.builder()
                .nickname("닉네임 수정")
                .build();

        // when
        profileService.updateProfile(requestDto, userDetails.getUser());

        // then
        // 주입한 닉네임과 수정된 닉네임 비교
        assertEquals(requestDto.getNickname(), profile.getNickname());
    }

    @Test
    @DisplayName("프로필 이미지 수정 테스트")
    void updateImageTest() throws IOException {
        // given
        // 모의 파일 생성
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String profileImage = file.getName();

        // findByUserId() 호출 시 profile 반환
        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));
        // fileUploader.upload() 호출 시 profileImage 반환
        when(fileUploader.upload(file)).thenReturn(profileImage);

        // when
        ProfileResponseDto response = profileService.updateImage(file, userDetails.getUser());

        // then
        // 주입한 파일명과 응답 파일명 비교
        assertEquals(response.getProfileImage(), profileImage);
    }

    @Test
    @DisplayName("프로필 관심사 등록 테스트")
    void addInterestsTest() {
        // given
        InterestMinor interestMinor1 = InterestMinor.builder()
                .id(1L)
                .minorName("축구")
                .build();
        InterestMinor interestMinor2 = InterestMinor.builder()
                .id(2L)
                .minorName("농구")
                .build();

        List<Long> minorIds = Arrays.asList(1L, 2L);
        InterestRequestDto requestDto = InterestRequestDto.builder()
                .minorId(minorIds)
                .build();
        Long request1 = requestDto.getMinorId().get(0);
        Long request2 = requestDto.getMinorId().get(1);

        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));
        when(interestMinorService.existsInterestMinor(request1)).thenReturn(interestMinor1);
        when(interestMinorService.existsInterestMinor(request2)).thenReturn(interestMinor2);

        // when
        profileService.addInterests(requestDto, user);

        // then
        verify(profileInterestService, times(2)).save(any(ProfileInterest.class));
    }

    @Test
    @DisplayName("관심 동호회 등록 테스트")
    void addLikeClubTest() {
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
        LikeClubRequestDto requestDto = LikeClubRequestDto.builder()
                .clubId(1L)
                .build();

        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));
        when(clubService.findClub(requestDto.getClubId())).thenReturn(club);


        //when
        profileService.addLikeClub(requestDto, user);

        //then
        verify(likeClubService, times(1)).save(any(LikeClub.class));
    }
}