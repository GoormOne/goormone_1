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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


}
