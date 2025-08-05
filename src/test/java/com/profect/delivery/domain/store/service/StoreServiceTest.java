package com.profect.delivery.domain.store.service;

import com.profect.delivery.domain.cart.repository.CartRepository;
import com.profect.delivery.domain.cart.service.CartService;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.domain.store.repository.StoreRepository;
import com.profect.delivery.global.entity.Store;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.exception.custom.BusinessErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {


    //가짜 DB 생성
    @Mock
    private StoreRepository storeRepository;

    //가까가 주입됨
    @InjectMocks
    private StoreService storeService;


    @Test
    void StoreId가_존재하면_상세정보를_반환한다() {
        // given
        UUID dummystoreId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        String dummyUserId = "U0001";
        UUID dummyCategoryId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000"); // ✅ 올바른 UUID 형식 사용

        Store store = Store.builder()
                .storeId(dummystoreId)
                .userId(dummyUserId)
                .storeName("테스트매장")
                .storeDescription("맛집")
                .storesCategoryId(dummyCategoryId)
                .zipCd("12345")
                .storePhone("010-1234-5678")
                .address1("서울시 어딘가")
                .address2("101동 202호")
                .storeLatitude(BigDecimal.valueOf(37.123))
                .storeLongitude(BigDecimal.valueOf(127.456))
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .deletedAt(null)
                .build();

        // when
        Mockito.when(storeRepository.findByStoreId(dummystoreId)).thenReturn(Optional.of(store));
        StoreDto result = storeService.findStoreById(dummystoreId.toString());

        // then
        assertEquals("테스트매장", result.getStoreName());
        assertEquals("맛집", result.getDescription());
        assertEquals("12345", result.getZip_cd());
    }

    @Test
    void 삭제된_매장이면_에러를_반환한다() {
        UUID storeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        String userId = "U0001";
        UUID categoryId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");

        Store store = Store.builder()
                .storeId(storeId)
                .userId(userId)
                .storeName("삭제된매장")
                .storeDescription("이 매장은 삭제됨")
                .storesCategoryId(categoryId)
                .zipCd("12345")
                .storePhone("010-1234-5678")
                .address1("서울시 어딘가")
                .address2("101동 202호")
                .storeLatitude(BigDecimal.valueOf(37.123))
                .storeLongitude(BigDecimal.valueOf(127.456))
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .deletedAt(LocalDateTime.now())
                .build();

        Mockito.when(storeRepository.findByStoreId(storeId)).thenReturn(Optional.of(store));

        BusinessException exception = assertThrows(BusinessException.class, () -> storeService.findStoreById(storeId.toString()));

        assertEquals(BusinessErrorCode.ALREADY_DELETED, exception.getDefaultErrorCode());
    }

    @Test
    void 매장_등록시_storeCategory가_없을때_에러를_반환한다() {

    }

//    @Test
//    void registerStore_정상적으로_매장_저장됨() {
//        String userId = "U0001";
//        UUID categoryId = UUID.randomUUID();
//        UUID storeId = UUID.randomUUID();
//
//
//        StoreRegisterDto requestDto = StoreRegisterDto.builder()
//                .storeName("테스트매장")
//                .userId(userId)
//                .category("1")
//                .storeDescription("맛집")
//                .address1("서울시 어딘가")
//                .address2("101동 202호")
//                .zipCd("12345")
//                .storePhone("010-1234-5678")
//                .storeLatitude(BigDecimal.valueOf(37.123))
//                .storeLongitude(BigDecimal.valueOf(127.456))
//                .openTime(LocalTime.of(9, 0))
//                .closeTime(LocalTime.of(18, 0))
//                .build();
//
//        Store savedStore = Store.builder()
//                .storeId(storeId)
//                .userId(userId)
//                .storeName("테스트매장")
//                .storeDescription("맛집")
//                .storesCategoryId((requestDto.getCategory()))
//                .zipCd("12345")
//                .storePhone("010-1234-5678")
//                .address1("서울시 어딘가")
//                .address2("101동 202호")
//                .storeLatitude(BigDecimal.valueOf(37.123))
//                .storeLongitude(BigDecimal.valueOf(127.456))
//                .openTime(LocalTime.of(9, 0))
//                .closeTime(LocalTime.of(18, 0))
//                .deletedAt(null)
//                .build();
//
//        // mock 저장 동작
//        Mockito.when(storeRepository.save(Mockito.any(Store.class)))
//                .thenReturn(savedStore);
//
//        // when
//        StoreDto result = storeService.save(store);
//
//        // then
//        assertNotNull(result);
//        assertEquals("테스트매장", result.getStoreName());
//        assertEquals("맛집", result.getDescription());
//        assertEquals("010-1234-5678", result.getStore_phone());
//    }





    }





