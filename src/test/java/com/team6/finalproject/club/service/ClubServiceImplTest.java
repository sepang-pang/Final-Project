package com.team6.finalproject.club.service;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.interest.entity.InterestMajor;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClubServiceImplTest {

    @Mock
    private ProfileService profileService;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private InterestMinorService interestMinorService;
    @InjectMocks
    private ClubServiceImpl clubServiceImpl;

    private ClubRequestDto requestDto;
    private Profile profile;
    private User user;
    private User user2;
    private InterestMajor interestMajor;
    private InterestMinor interestMinor;
    private Club savedClub;

    @BeforeEach
    public void setUp() {
        // 객체 생성
        requestDto = ClubRequestDto.builder()
                .name("축구 클럽")
                .minorId(1L)
                .description("축구 동호회입니다.")
                .trialAvailable(true)
                .isOnline(true)
                .openJoinType(true)
                .maxMember(100)
                .build();

        user = new User();
        user.setUsername("tpckd0533");
        user.setPassword("dbsehdwn12!");
        user.setEmail("tpckd0533@naver.com");
        user.setRole(UserRoleEnum.USER);
        user.setBirth("1997-04-11");

        profile = Profile.builder()
                .nickname("오세팡")
                .introduction("안녕하세요")
                .build();

        interestMajor = InterestMajor.builder()
                .majorName("스포츠")
                .build();

        interestMinor = InterestMinor.builder()
                .minorName("축구")
                .interestMajor(interestMajor)
                .build();

        savedClub = Club.builder()
                .username(user.getUsername())
                .nickName(profile.getNickname())
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .maxMember(requestDto.getMaxMember())
                .activityType(ActivityTypeEnum.ONLINE)
                .joinType(JoinTypeEnum.IMMEDIATE)
                .minor(interestMinor)
                .isTrialAvailable(requestDto.isTrialAvailable())
                .build();

    }

    @Test
    @DisplayName("동호회 생성 성공 테스트")
    public void testCreateClub_Success() {

        // interestMinorService.existsInterestMinor()의 반환값 설정
        when(interestMinorService.existsInterestMinor(1L)).thenReturn(interestMinor);
        when(profileService.findProfileByUserId(user.getId())).thenReturn(profile);

        // 테스트 대상 메서드 호출
        ClubResponseDto responseDto = clubServiceImpl.createClub(requestDto, user);

        // 생성된 ClubResponseDto의 각 필드 검증
        assertEquals(savedClub.getName(), responseDto.getName()); // 동호회 이름
        assertEquals(savedClub.getNickName(), responseDto.getNickName()); // 동호회 개설자
        assertEquals(savedClub.getDescription(), responseDto.getDescription()); // 동호회 소개
        assertEquals(savedClub.getMaxMember(), responseDto.getMaxMember()); // 동호회 최대 인원 수
        assertEquals(savedClub.getActivityType().getActivity(), responseDto.getActivityType()); // 활동 방식
        assertEquals(savedClub.getJoinType().getJoin(), responseDto.getJoinType()); // 가입 방식
        assertEquals(savedClub.isTrialAvailable(), responseDto.isTrialAvailable()); // 동호회 모임 체험 허용
        assertEquals(savedClub.getMinor().getMinorName(), responseDto.getMinor()); // 동호회의 소주제 이름

        assertEquals(user.getUsername(), responseDto.getUsername()); //username 검증
        assertEquals(interestMinor.getInterestMajor().getMajorName(), responseDto.getMajor()); // 대주제 이름 같은지 검증
        assertEquals(interestMinor.getMinorName(), responseDto.getMinor()); // 소주제 이름이 같은지 검증

    }

    @Test
    @DisplayName("이미 존재하는 동호회 이름 생성시 예외 처리 성공 테스트")
    public void testCreateClub_ClubNameAlreadyExists() {
        // clubRepository.findByName()의 반환값 설정
        when(clubRepository.findByActiveClubName("축구 클럽")).thenReturn(Optional.of(savedClub));

        // 예외를 던지는지 확인
        assertThrows(IllegalArgumentException.class, () -> {
            clubServiceImpl.createClub(requestDto, user);
        });
    }

    @Test
    @DisplayName("존재하지 않는 소주제를 선택시 예외 처리 성공 테스트")
    public void testCreateClub_InvalidMinorId() {
        // interestMinorService.existsInterestMinor()의 반환값 설정 (null로 설정하여 존재하지 않는 상태를 모방)
        when(interestMinorService.existsInterestMinor(1L)).thenReturn(null);

        // 예외를 던지는지 확인
        assertThrows(NullPointerException.class, () -> {
            clubServiceImpl.createClub(requestDto, user);
        });
    }

    @Test
    @DisplayName("동호회 폐쇄 성공 테스트")
    public void testDeleteClub_Success() {
        Long clubId = 1L;
        when(clubRepository.findByActiveIdAndUsername(eq(clubId), eq(user.getUsername())))
                .thenReturn(Optional.of(savedClub));

        ResponseEntity<ApiResponseDto> response = clubServiceImpl.deleteClub(clubId, user);

        assertEquals(HttpStatus.OK, response.getStatusCode()); // 응답 코드 확인
        assertTrue(savedClub.isDeleted()); // 상태값이 true 로 즉, soft - delete 되었는지 확인

        // clubRepositoryCustom의 findByIdAndUsername 메서드가 호출되었는지 검증
        verify(clubRepository, times(1)).findByActiveIdAndUsername(eq(clubId), eq(user.getUsername()));
    }

    @Test
    @DisplayName("삭제된 동호회 조회 테스트")
    public void testGetDeleteClub() {
        Long clubId = 1L;
        when(clubRepository.findByActiveIdAndUsername(eq(clubId), eq(user.getUsername())))
                .thenReturn(Optional.of(savedClub));

        // 동호회 삭제
        clubServiceImpl.deleteClub(clubId, user);

        // 삭제된 동호회 조회 메서드 호출, 예외가 발생해야 함
        assertThrows(IllegalArgumentException.class, () -> {
            clubServiceImpl.findClub(clubId);
        });

        assertTrue(savedClub.isDeleted());
    }
}