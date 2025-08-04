package com.profect.delivery.domain.cart.service;

import com.profect.delivery.domain.cart.dto.AddCartDto;
import com.profect.delivery.domain.cart.repository.CartItemRepository;
import com.profect.delivery.domain.cart.repository.CartRepository;
import com.profect.delivery.global.entity.Cart;
import com.profect.delivery.global.exception.InvalidUuidFormatException;
import com.profect.delivery.global.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// ✅ JUnit Assertion 메서드 정리
// - 테스트 결과 검증할 때 사용하는 메서드들
// - org.junit.jupiter.api.Assertions 임포트 필요
//
// assertEquals(expected, actual)      // 기대값과 실제값이 같은지 비교
// assertNotEquals(unexpected, actual) // 기대값과 다르면 성공
// assertTrue(condition)              // 조건이 true일 때 성공
// assertFalse(condition)             // 조건이 false일 때 성공
// assertNull(object)                 // 객체가 null이면 성공
// assertNotNull(object)              // 객체가 null이 아니면 성공
// assertThrows(Exception.class, () -> { ... }) // 예외가 발생해야 성공
// assertAll(() -> {}, ...)           // 여러 개의 assert를 그룹으로 실행
// assertArrayEquals(expected, actual)// 배열이 같은지 비교

// ✅ Mockito 메서드 정리 (Mock 객체 관련)
// - org.mockito.Mockito 임포트 필요
//
// mock(Class.class)                  // Mock 객체 생성
// when(mock.method()).thenReturn()  // 특정 호출에 대해 가짜 응답 설정
// verify(mock).method()             // 해당 메서드가 호출되었는지 검증
// verify(mock, times(n)).method()   // 메서드가 정확히 n번 호출되었는지 검증
// any(), anyString(), anyInt() 등   // 파라미터 조건 설정할 때 사용

class CartServiceTest {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CartService cartService;

    @BeforeEach
    void setUp() {  // 생성자 주입
        cartRepository = mock(CartRepository.class);
        cartService = mock(CartService.class);
        cartItemRepository = mock(CartItemRepository.class);
    }

    @Test
    void 사용자의_카트가_존재하면_리스트를_반환한다() {
        // given : 테스트 준비 단계
        String userId = "user001";
        Cart dummyCart = new Cart(); // 필요 시 필드 설정
        when(cartRepository.findByUserId(userId)).thenReturn(List.of(dummyCart));

        // when : 실제 로직 실행 단계
        List<Cart> result = cartService.getCartsWithItemsByUserId(userId);

        // then : 검증
        assertEquals(1, result.size());
        verify(cartRepository, times(1)).findByUserId(userId);
    }

    @Test
    void 사용자의_카트가_없으면_NotFoundException을_던진다() {
        // given
        String userId = "user002";
        when(cartRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        // when & then
        assertThrows(NotFoundException.class, () -> {
            cartService.getCartsWithItemsByUserId(userId);
        });
        verify(cartRepository, times(1)).findByUserId(userId);
    }

    @Test
    void saveCart_정상적으로_카트_저장됨() {
        // given
        String userId = "user001";
        String storeId = UUID.randomUUID().toString();
        String menuId = UUID.randomUUID().toString();

        List<AddCartDto> cartDtoList = List.of(
                new AddCartDto(menuId, "2")
        );

        Cart mockCart = new Cart();
        mockCart.setCartId(UUID.randomUUID());

        // when
        when(cartRepository.save(any(Cart.class)))
                .thenReturn(mockCart);

        // then
        UUID result = cartService.saveCart(cartDtoList, userId, storeId);

        assertNotNull(result);
        assertEquals(mockCart.getCartId(), result);
    }

    @Test
    void saveCart_잘못된_storeId_형식이면_예외발생() {
        // given
        String userId = "user001";
        String invalidStoreId = "invalid-uuid";
        List<AddCartDto> cartDtoList = List.of();

        // then
        assertThrows(InvalidUuidFormatException.class, () -> {
            cartService.saveCart(cartDtoList, userId, invalidStoreId);
        });
    }

    @Test
    void deleteCartByCartId_존재할때_삭제성공() {
        // given
        UUID cartId = UUID.randomUUID();
        when(cartItemRepository.existsById(cartId)).thenReturn(true);

        // when
        boolean result = cartService.deleteCartByCartId(cartId);

        // then
        assertTrue(result);
        verify(cartItemRepository).deleteById(cartId);
        verify(cartRepository).deleteById(cartId);
    }

    @Test
    void deleteCartByCartId_존재하지않을때_삭제실패() {
        // given
        UUID cartId = UUID.randomUUID();
        when(cartItemRepository.existsById(cartId)).thenReturn(false);

        // when
        boolean result = cartService.deleteCartByCartId(cartId);

        // then
        assertFalse(result);
        verify(cartItemRepository, never()).deleteById(any());
        verify(cartRepository, never()).deleteById(any());
    }

    @Test
    void cartToCartInfoDto() {
    }
}