//package com.team6.finalproject.profile.profileinterest.service;
//
//import com.team6.finalproject.advice.custom.NotExistResourceException;
//import com.team6.finalproject.club.interest.entity.InterestMinor;
//import com.team6.finalproject.club.interest.service.InterestMinorService;
//import com.team6.finalproject.profile.dto.InterestRequestDto;
//import com.team6.finalproject.profile.entity.Profile;
//import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
//import com.team6.finalproject.profile.profileinterest.repository.ProfileInterestRepository;
//import com.team6.finalproject.profile.service.ProfileService;
//import com.team6.finalproject.security.UserDetailsImpl;
//import com.team6.finalproject.user.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static com.team6.finalproject.user.entity.UserRoleEnum.USER;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProfileInterestServiceImplTest {
//    @InjectMocks
//    private ProfileInterestServiceImpl profileInterestService;
//    @Mock
//    private InterestMinorService interestMinorService;
//    @Mock
//    private ProfileInterestRepository profileInterestRepository;
//    @Mock
//    private ProfileService profileService;
//
//    private User user;
//    private UserDetailsImpl userDetails;
//    private Profile profile;
//
//    @BeforeEach
//    void setup() {
//        // 유저 생성(Setter 지우고 builder 사용 시 변경)
//        user = new User("유저네임", "비밀번호", "이메일", "2020-02-02", USER);
//        user.setId(1L);
//        userDetails = new UserDetailsImpl(user);
//        //프로필 생성
//        profile = Profile.builder()
//                .nickname("닉네임")
//                .introduction("소개글")
//                .locate("서울시")
//                .user(user)
//                .build();
//    }
//
//    @Test
//    @DisplayName("프로필 관심사 등록 테스트")
//    void addInterestsTest() throws NotExistResourceException {
//        // given
//        InterestMinor interestMinor1 = InterestMinor.builder()
//                .id(1L)
//                .minorName("축구")
//                .build();
//        InterestMinor interestMinor2 = InterestMinor.builder()
//                .id(2L)
//                .minorName("농구")
//                .build();
//
//        List<Long> minorIds = Arrays.asList(1L, 2L);
//        InterestRequestDto requestDto = new InterestRequestDto();
//        requestDto.setMinorId(minorIds);
//
//        Long request1 = requestDto.getMinorId().get(0);
//        Long request2 = requestDto.getMinorId().get(1);
//
//        when(profileService.findProfileByUserId(user.getId())).thenReturn(profile);
//        when(interestMinorService.existsInterestMinor(request1)).thenReturn(interestMinor1);
//        when(interestMinorService.existsInterestMinor(request2)).thenReturn(interestMinor2);
//
//        // when
//        profileInterestService.addInterests(requestDto, user);
//
//        // then
//        verify(profileInterestRepository, times(2)).save(any(ProfileInterest.class));
//    }
//}