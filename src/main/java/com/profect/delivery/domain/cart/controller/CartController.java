package com.profect.delivery.domain.cart.controller;

import com.profect.delivery.domain.cart.repository.CartItemRepository;
import com.profect.delivery.domain.cart.repository.CartRepository;
import com.profect.delivery.domain.cart.service.CartService;
import com.profect.delivery.domain.cart.DTO.AddCartDto;
import com.profect.delivery.domain.cart.DTO.CartInfoDto;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.entity.Cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;


    @GetMapping()
    public ResponseEntity<ApiResponse<CartInfoDto>> getCart(){
        //ControllerAdvice에서 model로 받기
        String userId = "user011";


        log.info("userId={}",userId);
        List<Cart> carts = cartService.getCartsWithItemsByUserId(userId);

        CartInfoDto cartInfoDto = cartService.CartToCartInfoDto(carts, userId);

        ApiResponse<CartInfoDto> response = ApiResponse.success(cartInfoDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST 요청: /cart
    @PostMapping("/{storeId}")
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody List<AddCartDto> addCartDtoList,
            @PathVariable String storeId
    ){
        //model 전달
        String userId = "user001";
        log.info("storeId={}", storeId);

        UUID cartId = cartService.saveCart(addCartDtoList, userId, storeId);

        log.info("complete add cart cartId={}", cartId);


        return new  ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
    }









}
