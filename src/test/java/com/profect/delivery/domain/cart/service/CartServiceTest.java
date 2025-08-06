//package com.profect.delivery.domain.cart.service;
//
//import com.profect.delivery.domain.cart.dto.AddCartDto;
//import com.profect.delivery.domain.cart.repository.CartItemRepository;
//import com.profect.delivery.domain.cart.repository.CartRepository;
//import com.profect.delivery.global.entity.Cart;
//import com.profect.delivery.global.entity.CartItem;
//import com.profect.delivery.global.exception.BusinessException;
//import com.profect.delivery.global.exception.InvalidUuidFormatException;
//import com.profect.delivery.global.exception.NotFoundException;
//import com.profect.delivery.global.exception.custom.DefaultErrorCode;
//import com.profect.delivery.global.exception.custom.OrderErrorCode;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//// ✅ JUnit Assertion 메서드 정리
//// - 테스트 결과 검증할 때 사용하는 메서드들
//// - org.junit.jupiter.api.Assertions 임포트 필요
////
//// assertEquals(expected, actual)      // 기대값과 실제값이 같은지 비교
//// assertNotEquals(unexpected, actual) // 기대값과 다르면 성공
//// assertTrue(condition)              // 조건이 true일 때 성공
//// assertFalse(condition)             // 조건이 false일 때 성공
//// assertNull(object)                 // 객체가 null이면 성공
//// assertNotNull(object)              // 객체가 null이 아니면 성공
//// assertThrows(Exception.class, () -> { ... }) // 예외가 발생해야 성공
//// assertAll(() -> {}, ...)           // 여러 개의 assert를 그룹으로 실행
//// assertArrayEquals(expected, actual)// 배열이 같은지 비교
//
//// ✅ Mockito 메서드 정리 (Mock 객체 관련)
//// - org.mockito.Mockito 임포트 필요
////
//// mock(Class.class)                  // Mock 객체 생성
//// when(mock.method()).thenReturn()  // 특정 호출에 대해 가짜 응답 설정
//// verify(mock).method()             // 해당 메서드가 호출되었는지 검증
//// verify(mock, times(n)).method()   // 메서드가 정확히 n번 호출되었는지 검증
//// any(), anyString(), anyInt() 등   // 파라미터 조건 설정할 때 사용
//
//class CartServiceTest {
//    private CartRepository cartRepository;
//    private CartItemRepository cartItemRepository;
//    private CartService cartService;
//
//    @BeforeEach
//    void setUp() {
//        cartRepository = mock(CartRepository.class);
//        cartItemRepository = mock(CartItemRepository.class);
//        cartService = new CartService(cartRepository, cartItemRepository);
//    }
//
//    @Test
//    void 사용자의_카트가_존재하면_리스트를_반환한다() {
//        // given : 테스트 준비 단계
//        String userId = "user001";
//        Cart dummyCart = new Cart(); // 필요 시 필드 설정
//        when(cartRepository.findByUserId(userId)).thenReturn(List.of(dummyCart));
//
//        // when : 실제 로직 실행 단계
//        List<Cart> result = cartService.getCartsWithItemsByUserId(userId);
//
//        // then : 검증
//        assertEquals(1, result.size());
//        verify(cartRepository, times(1)).findByUserId(userId);
//    }
//
////    @Test
////    void 사용자의_카트가_없으면_NotFoundException을_던진다() {
////        // given
////        String userId = "user002";
////        when(cartRepository.findByUserId(userId)).thenReturn(Collections.emptyList());
////
////        // when & then
////        assertThrows(BusinessException.class, () -> {
////            cartService.getCartsWithItemsByUserId(userId);
////        });
////        verify(cartRepository, times(1)).findByUserId(userId);
////    }
//@Test
//void 사용자의_카트가_없으면_BusinessException_던지고_에러코드는_NOT_FOUND_CART이다() {
//    // given
//    String userId = "user002";
//    when(cartRepository.findByUserId(userId)).thenReturn(Collections.emptyList());
//
//    // when
//    BusinessException ex = assertThrows(BusinessException.class, () -> {
//        cartService.getCartsWithItemsByUserId(userId);
//    });
//
//    // then
//    assertEquals(OrderErrorCode.NOT_FOUND_CART.getMessage(), ex.getMessage());
//    verify(cartRepository, times(1)).findByUserId(userId);
//}
//
//    @Test
//    void saveCart_정상적으로_카트_저장됨() {
//        // given
//        String userId = "user001";
//        String storeId = UUID.randomUUID().toString();
//        String menuId = UUID.randomUUID().toString();
//
//        List<AddCartDto> cartDtoList = List.of(
//                new AddCartDto(menuId, "2")
//        );
//
//        Cart mockCart = new Cart();
//        mockCart.setCartId(UUID.randomUUID());
//
//        // when
//        when(cartRepository.save(any(Cart.class)))
//                .thenReturn(mockCart);
//
//        // then
//        UUID result = cartService.saveCart(cartDtoList, userId, storeId);
//
//        assertNotNull(result);
//        assertEquals(mockCart.getCartId(), result);
//    }
//
//
//
//
//    @Test
//    void deleteCartByCartId_존재할때_삭제성공() {
//        // given@Test
//        //    void saveCart_잘못된_storeId_형식이면_예외발생() {
//        //        // given
//        //        String userId = "user001";
//        //        String invalidStoreId = "invalid-uuid";
//        //        List<AddCartDto> cartDtoList = List.of();
//        //
//        //        // then
//        //        assertThrows(InvalidUuidFormatException.class, () -> {
//        //            cartService.saveCart(cartDtoList, userId, invalidStoreId);
//        //        });
//        //    }
//        UUID cartId = UUID.randomUUID();
//        when(cartItemRepository.existsById(cartId)).thenReturn(true);
//
//        // when
//        boolean result = cartService.deleteCartByCartId(cartId);
//
//        // then
//        assertTrue(result);
//        verify(cartItemRepository).deleteByCart(any(Cart.class));
//        verify(cartRepository).deleteById(cartId);
//    }
//
//    @Test
//    void deleteCartByCartId_존재하지않을때_삭제실패() {
//        // given
//        UUID cartId = UUID.randomUUID();
//        when(cartItemRepository.existsById(cartId)).thenReturn(false);
//
//        // when
//        boolean result = cartService.deleteCartByCartId(cartId);
//
//        // then
//        assertFalse(result);
//        verify(cartItemRepository, never()).deleteById(any());
//        verify(cartRepository, never()).deleteById(any());
//    }
//
//    @Test
//    void updateCartItem_정상수정_성공리턴() {
//        // given
//        UUID cartItemId = UUID.randomUUID();
//        CartItem mockItem = CartItem.builder()
//                .cartItemId(cartItemId)
//                .menuId(UUID.randomUUID())
//                .quantity(1)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(mockItem));
//
//        List<AddCartDto> dtoList = new ArrayList<>();
//        AddCartDto dto = new AddCartDto();
//        dto.setMenuId(UUID.randomUUID().toString());
//        dto.setQuantity("3");
//        dtoList.add(dto);
//
//        // when
//        boolean result = cartService.updateCartItem(cartItemId, dtoList, "user001");
//
//        // then
//        assertTrue(result);
//        assertEquals(3, mockItem.getQuantity());
//        assertEquals(UUID.fromString(dto.getMenuId()), mockItem.getMenuId());
//        assertNotNull(mockItem.getUpdatedAt());
//    }
//
//
////    void updateCartItem_엔티티없음_예외발생() {
////        // given
////        UUID cartItemId = UUID.randomUUID();
////        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());
////
////        // when & then
//////        assertThrows(IllegalArgumentException.class, () -> {
//////            cartService.updateCartItem(cartItemId, new ArrayList<>(), "user001");
//////        });
////
////    }
//    @Test
//    void updateCartItem_엔티티없음_예외발생() {
//        // given
//        UUID cartItemId = UUID.randomUUID();
//        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());
//
//        // when
//        BusinessException ex = assertThrows(BusinessException.class, () -> {
//            cartService.updateCartItem(cartItemId, new ArrayList<>(), "user001");
//        });
//
//        // then
//        assertEquals(OrderErrorCode.NOT_FOUND_CART.getMessage(), ex.getMessage());
//        verify(cartItemRepository, times(1)).findById(cartItemId);
//    }
//
//    @Test
//    void updateCartItem_잘못된입력_예외로_false반환() {
//        // given
//        UUID cartItemId = UUID.randomUUID();
//        CartItem mockItem = new CartItem();
//        mockItem.setCartItemId(cartItemId);
//        mockItem.setQuantity(1);
//        mockItem.setMenuId(UUID.randomUUID());
//        mockItem.setCreatedAt(LocalDateTime.now());
//
//        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(mockItem));
//
//        AddCartDto badDto = new AddCartDto();
//        badDto.setMenuId("not-a-uuid");  // UUID 변환 실패 유도
//        badDto.setQuantity("abc");       // 파싱 실패 유도
//
//        List<AddCartDto> dtoList = List.of(badDto);
//
//        // when
//        boolean result = cartService.updateCartItem(cartItemId, dtoList, "user001");
//
//        // then
//        assertFalse(result);
//    }
//}