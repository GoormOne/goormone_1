//package com.profect.delivery.domain.users.service;
//
//import com.profect.delivery.domain.users.dto.UserAddressesRequestDto;
//import com.profect.delivery.domain.users.repository.UserAdressRepository;
//import com.profect.delivery.global.entity.UserAddress;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserAddressServiceTest {
//    @InjectMocks
//    private UserAddressService userAddressService;
//
//    @Mock
//    private UserAdressRepository userAdressRepository;
//
//    private UserAddress userAddress1;
//    private UserAddress userAddress2;
//
//    @BeforeEach
//    void setUp() {
//        // setter를 사용하여 UserAddress 객체들을 초기화합니다.
//        userAddress1 = new UserAddress();
//        userAddress1.setAddressCd(UUID.randomUUID());
//        userAddress1.setUserId("testUser");
//        userAddress1.setAddressName("집");
//        userAddress1.setAddress1("서울시 강남구");
//        userAddress1.setAddress2("테스트 아파트");
//        userAddress1.setZipCd("123456");
//        userAddress1.setUserLatitude(BigDecimal.valueOf(37.123456));
//        userAddress1.setUserLongitude(BigDecimal.valueOf(127.654321));
//        userAddress1.setIsDefault(true);
//        userAddress1.setCreatedAt(Timestamp.valueOf("2025-08-04 10:00:00"));
//
//        userAddress2 = new UserAddress();
//        userAddress2.setAddressCd(UUID.randomUUID());
//        userAddress2.setUserId("testUser");
//        userAddress2.setAddressName("회사");
//        userAddress2.setAddress1("경기도 성남시");
//        userAddress2.setAddress2("테스트 빌딩");
//        userAddress2.setZipCd("654321");
//        userAddress2.setUserLatitude(BigDecimal.valueOf(37.987654));
//        userAddress2.setUserLongitude(BigDecimal.valueOf(127.456789));
//        userAddress2.setIsDefault(false);
//        userAddress2.setCreatedAt(Timestamp.valueOf("2025-08-04 11:00:00"));
//    }
//
//    @Test
//    @DisplayName("사용자 ID로 주소 목록 조회")
//    void findByUserId() {
//        //Given
//        String userId = "testUser";
//        List<UserAddress> addresses = Arrays.asList(userAddress1, userAddress2);
//        when(userAdressRepository.findByUserId(userId)).thenReturn(addresses);
//
//
//        // When
//        List<UserAddress> foundAddresses = userAddressService.findByUserId(userId);
//
//        // Then
//        assertThat(foundAddresses).hasSize(2);
//        assertThat(foundAddresses.get(0).getUserId()).isEqualTo(userId);
//        assertThat(foundAddresses.get(1).getAddress1()).isEqualTo("경기도 성남시");
//        verify(userAdressRepository, times(1)).findByUserId(userId);
//    }
//
//    @Test
//    @DisplayName("새로운 사용자 주소 생성 성공")
//    void CreateUserAddress_Success() {
//        // Given
//        String userId = "testUser";
//
//        // setter를 사용하여 DTO 객체 생성
//        UserAddressesRequestDto mockRequestDto = mock(UserAddressesRequestDto.class);
//
//        // DTO의 toEntity 메서드가 반환할 가짜 UserAddress 객체 설정
//        UserAddress newUserAddress = UserAddress.builder()
//                .addressCd(UUID.randomUUID())
//                .userId(userId)
//                .addressName("새 주소")
//                .address1("부산광역시 해운대구")
//                .address2("마린시티")
//                .zipCd("48101")
//                .userLatitude(BigDecimal.valueOf(35.123456))
//                .userLongitude(BigDecimal.valueOf(129.654321))
//                .isDefault(false)
//                .build();
//
//        when(mockRequestDto.toEntity(userId)).thenReturn(newUserAddress);
//
//        // When
//        userAddressService.CreateUserAddress(mockRequestDto, userId);
//
//        // Then
//        // userAdressRepository의 save 메서드가 newUserAddress 객체로 한 번 호출되었는지 검증
//        verify(userAdressRepository, times(1)).save(newUserAddress);
//        verify(mockRequestDto, times(1)).toEntity(userId);
//    }
//    }