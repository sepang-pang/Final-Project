//package com.team6.finalproject.user.inquiry.service;
//
//import com.team6.finalproject.advice.custom.NotExistResourceException;
//import com.team6.finalproject.user.inquiry.dto.InquiryRequestDto;
//import com.team6.finalproject.user.inquiry.dto.InquiryResponseDto;
//import com.team6.finalproject.user.entity.User;
//import com.team6.finalproject.user.inquiry.entity.Inquiry;
//import com.team6.finalproject.user.inquiry.entity.InquiryTypeEnum;
//import com.team6.finalproject.user.inquiry.repository.InquiryRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.team6.finalproject.user.entity.UserRoleEnum.USER;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class InquiryServiceImplTest {
//    @InjectMocks
//    private InquiryServiceImpl inquiryService;
//    @Mock
//    private InquiryRepository inquiryRepository;
//
//    private User user;
//    private Inquiry inquiry;
//    private InquiryRequestDto requestDto;
//
//    @BeforeEach
//    void setup() {
//        // 유저 생성(Setter 지우고 builder 사용 시 변경)
//        user = new User("유저네임", "비밀번호", "이메일", "2020-02-02", USER);
//        user.setId(1L);
//        // 요청 생성
//        requestDto = new InquiryRequestDto();
//        requestDto.setInquiryId(1L);
//        requestDto.setInquiryType(InquiryTypeEnum.valueOf("FEATURE_PROPOSAL"));
//        requestDto.setDescription("문의 내용");
//        // 문의 생성
//        inquiry = Inquiry.builder()
//                .inquiryType(requestDto.getInquiryType())
//                .description(requestDto.getDescription())
//                .user(user)
//                .build();
//    }
//
//    @Test
//    @DisplayName("문의 등록 테스트")
//    void createInquiryTest() {
//        //when
//        inquiryService.createInquiry(requestDto, user);
//
//        //then
//        verify(inquiryRepository, times(1)).save(any(Inquiry.class));
//    }
//
//    @Test
//    @DisplayName("문의 단건 조회 테스트")
//    void getInquiryTest() throws NotExistResourceException {
//        //given
//        Long id = 1L;
//
//        when(inquiryRepository.findByIdAndUserId(id, user.getId())).thenReturn(Optional.of(inquiry));
//
//        //when
//        InquiryResponseDto response = inquiryService.getInquiry(id, user);
//
//        //then
//        assertEquals(inquiry.getDescription(), response.getDescription());
//        assertEquals(inquiry.getInquiryType(), response.getInquiryType());
//    }
//
//    @Test
//    @DisplayName("문의 전체 조희 테스트")
//    void getAllInquiryTest() {
//        //given
//        List<Inquiry> mockInquiries = new ArrayList<>();
//
//        when(inquiryRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId())).thenReturn(mockInquiries);
//
//        //when
//        List<InquiryResponseDto> response = inquiryService.getAllInquiry(user);
//
//        //then
//        assertEquals(mockInquiries.size(), response.size());
//    }
//
//    @Test
//    @DisplayName("문의 수정 테스트")
//    void updateInquiryTest() throws NotExistResourceException {
//        //given
//        when(inquiryRepository.findByIdAndUserId(requestDto.getInquiryId(), user.getId())).thenReturn(Optional.of(inquiry));
//
//        requestDto.setInquiryType(InquiryTypeEnum.valueOf("CUSTOMER_SUPPORT"));
//        requestDto.setDescription("문의 내용 수정");
//
//        //when
//        InquiryResponseDto response = inquiryService.updateInquiry(requestDto, user);
//
//        //then
//        assertEquals(requestDto.getDescription(), response.getDescription());
//        assertEquals(requestDto.getInquiryType(), response.getInquiryType());
//    }
//}