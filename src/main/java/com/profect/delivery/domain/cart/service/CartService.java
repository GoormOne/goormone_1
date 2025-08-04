package com.profect.delivery.domain.cart.service;

import com.profect.delivery.domain.cart.dto.AddCartDto;
import com.profect.delivery.domain.cart.dto.CartDto;
import com.profect.delivery.domain.cart.dto.CartInfoDto;
import com.profect.delivery.domain.cart.dto.ItemDto;
import com.profect.delivery.domain.cart.repository.CartItemRepository;
import com.profect.delivery.domain.cart.repository.CartRepository;
import com.profect.delivery.global.entity.Cart;
import com.profect.delivery.global.entity.CartItem;
import com.profect.delivery.global.exception.InvalidUuidFormatException;
import com.profect.delivery.global.exception.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public UUID saveCart(List<AddCartDto> addCartDtoList, String userId, String storeId){

        UUID storeUuid;
        try {
            storeUuid = UUID.fromString(storeId);
        }catch (IllegalArgumentException e){
            throw new InvalidUuidFormatException("storeId is not valid");
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStoreId(storeUuid);
        cart.setCartStatus(Cart.CartStatus.ACTIVE);

        Cart savedCart = cartRepository.save(cart);

        log.info("Cart saved successfully {}", savedCart);

        List<CartItem> cartItems = new ArrayList<>();
        for(AddCartDto dto : addCartDtoList) {
            UUID menuId = UUID.fromString(dto.getMenuId());
            CartItem item = new CartItem(menuId, Integer.parseInt(dto.getQuantity()), savedCart);
            cartItems.add(item);
        }
        cartItemRepository.saveAll(cartItems);

        cart.setCartItems(cartItems);
        log.info("save cart {}", cart.toString());



        return savedCart.getCartId();

    }

    public List<Cart> getCartsWithItemsByUserId(String userId){

        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new NotFoundException("해당 사용자의 카트가 존재하지 않습니다.");
        }
        return carts;


    }

    public CartInfoDto CartToCartInfoDto(List<Cart> carts, String userId){
        List<CartDto> cartDtos = carts.stream()
                .map(cart -> new CartDto(
                        cart.getCartId().toString(),
                        cart.getStoreId().toString(),
                        cart.getCartStatus().name(),
                        cart.getCartItems().stream()
                                .map(item -> new ItemDto(
                                        item.getCartItemId().toString(),
                                        item.getMenuId().toString(),
                                        item.getQuantity()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new CartInfoDto(userId, cartDtos);


    }


    public boolean deleteCartByCartId(UUID cartUuid) {
        if (cartItemRepository.existsById(cartUuid)) {

            cartItemRepository.deleteByCart((new Cart(cartUuid)));
            cartRepository.deleteById(cartUuid);
            return true;
        } else {
            log.warn("삭제 실패 - 존재하지 않음: {}", cartUuid);
            return false;
        }

    }


    public List<CartItem> saveCartItems(List<CartItem> cartItems) {

        return cartItemRepository.saveAll(cartItems);

    }

    public boolean deleteCartItemById(String cartItemId) {
        UUID cartItemUuid = UUID.fromString(cartItemId);

        if (cartItemRepository.existsById(cartItemUuid)){
            cartItemRepository.deleteById(cartItemUuid);
            return true;
        }else{
            log.warn("삭제 실패 - 카트 아이템 존재하지 않음: {}", cartItemUuid);
            return false;
        }

    }

    @Transactional
    public void updateCartItem(UUID cartItemId, List<AddCartDto> addCartList) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 CartItem이 존재하지 않습니다."));

        // 예시: 첫 번째 AddCartDto로만 수정 (단일 아이템 수정 기준)
        AddCartDto dto = addCartList.get(0);
        cartItem.setMenuId(UUID.fromString(dto.getMenuId()));
        cartItem.setQuantity(Integer.parseInt(dto.getQuantity()));
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItem.setUpdatedBy("user001"); // 유저아이디 넣기

        // JPA는 영속 상태라 save 호출 없이도 트랜잭션 끝나면 자동 반영됨
    }
}
