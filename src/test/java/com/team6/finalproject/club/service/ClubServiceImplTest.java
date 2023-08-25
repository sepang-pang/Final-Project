//package com.team6.finalproject.club.service;
//
//import com.team6.finalproject.club.dto.ClubRequestDto;
//import com.team6.finalproject.club.dto.ClubResponseDto;
//import com.team6.finalproject.club.entity.Club;
//import com.team6.finalproject.club.enums.ActivityTypeEnum;
//import com.team6.finalproject.club.enums.JoinTypeEnum;
//import com.team6.finalproject.club.interest.entity.InterestMajor;
//import com.team6.finalproject.club.interest.entity.InterestMinor;
//import com.team6.finalproject.club.interest.service.InterestMinorService;
//import com.team6.finalproject.club.repository.ClubRepository;
//import com.team6.finalproject.profile.entity.Profile;
//import com.team6.finalproject.profile.repository.ProfileRepository;
//import com.team6.finalproject.profile.service.ProfileService;
//import com.team6.finalproject.profile.service.ProfileServiceImpl;
//import com.team6.finalproject.user.entity.User;
//import com.team6.finalproject.user.entity.UserRoleEnum;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ClubServiceImplTest {
//    @Mock
//    private InterestMinorService interestMinorService;
//    @Mock
//    private ClubRepository clubRepository;
//
//    @Autowired
//    private ProfileServiceImpl profileService;
//
//    @InjectMocks
//    private ClubServiceImpl clubServiceImpl;
//    private ClubRequestDto requestDto;
//    private Profile profile;
//    private User user;
//    private InterestMajor interestMajor;
//    private InterestMinor interestMinor;
//    private Club savedClub;
//
//    @BeforeEach
//    public void setUp() {
//        // 객체 생성
//        requestDto = ClubRequestDto.builder()
//                .name("축구 클럽")
//                .minorId(1L)
//                .description("축구 동호회입니다.")
//                .trialAvailable(true)
//                .isOnline(true)
//                .openJoinType(true)
//                .maxMember(100)
//                .build();
//
//        user = new User();
//        user.setUsername("tpckd0533");
//        user.setPassword("dbsehdwn12!");
//        user.setEmail("tpckd0533@naver.com");
//        user.setRole(UserRoleEnum.USER);
//        user.setBirth("1997-04-11");
//
//        profile = Profile.builder()
//                .nickname("오세팡팡")
//                .introduction("안녕하세용")
//                .build();
//
//        interestMajor = InterestMajor.builder()
//                .majorName("스포츠")
//                .build();
//
//        interestMinor = InterestMinor.builder()
//                .minorName("축구")
//                .interestMajor(interestMajor)
//                .build();
//
//       savedClub = Club.builder()
//                .username(user.getUsername())
//                .nickName(profile.getNickname())
//                .name(requestDto.getName())
//                .description(requestDto.getDescription())
//                .maxMember(requestDto.getMaxMember())
//                .activityType(ActivityTypeEnum.ONLINE)
//                .joinType(JoinTypeEnum.IMMEDIATE)
//                .minor(interestMinor)
//                .isTrialAvailable(requestDto.isTrialAvailable())
//                .build();
//
//    }
//
//    @Test // 동호회 생성 성공 테스트
//    public void testCreateClub_Success() {
//
//        profile = profileService.findProfileByUserId(user.getId());
//
//        // interestMinorService.existsInterestMinor()의 반환값 설정
//       when(interestMinorService.existsInterestMinor(1L)).thenReturn(interestMinor);
//
//        // 테스트 대상 메서드 호출
//        ClubResponseDto responseDto = clubServiceImpl.createClub(requestDto, user);
//
//        // 생성된 ClubResponseDto의 각 필드 검증
//        assertEquals(savedClub.getName(), responseDto.getName()); // 동호회 이름
//        assertEquals(savedClub.getDescription(), responseDto.getDescription()); // 동호회 소개
//        assertEquals(savedClub.getMaxMember(), responseDto.getMaxMember()); // 동호회 최대 인원 수
//        assertEquals(savedClub.getActivityType().getActivity(), responseDto.getActivityType()); // 활동 방식
//        assertEquals(savedClub.getJoinType().getJoin(), responseDto.getJoinType()); // 가입 방식
//        assertEquals(savedClub.isTrialAvailable(), responseDto.isTrialAvailable()); // 동호회 모임 체험 허용
//        assertEquals(savedClub.getMinor().getMinorName(), responseDto.getMinor()); // 동호회의 소주제 이름
//
//        assertEquals(user.getUsername(), responseDto.getUsername()); //username 검증
//        assertEquals(interestMinor.getInterestMajor().getMajorName(), responseDto.getMajor()); // 대주제 이름 같은지 검증
//        assertEquals(interestMinor.getMinorName(), responseDto.getMinor()); // 소주제 이름이 같은지 검증
//
//    }
//
//    @Test // 이미 존재하는 동호회 이름 생성시 예외 처리 성공 테스트
//    public void testCreateClub_ClubNameAlreadyExists() {
//        // clubRepository.findByName()의 반환값 설정
//        when(clubRepository.findByName("축구 클럽")).thenReturn(Optional.of(savedClub));
//
//        // 예외를 던지는지 확인
//        assertThrows(IllegalArgumentException.class, () -> {
//            clubServiceImpl.createClub(requestDto, user);
//        });
//    }
//
//    @Test // 존재하지 않는 소주제를 선택시 예외 처리 성공 테스트
//    public void testCreateClub_InvalidMinorId() {
//        // interestMinorService.existsInterestMinor()의 반환값 설정 (null로 설정하여 존재하지 않는 상태를 모방)
//        when(interestMinorService.existsInterestMinor(1L)).thenReturn(null);
//
//        // 예외를 던지는지 확인
//        assertThrows(NullPointerException.class, () -> {
//            clubServiceImpl.createClub(requestDto, user);
//        });
//    }
//
//
//}