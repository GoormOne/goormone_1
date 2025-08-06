package com.profect.delivery.domain.cart.controller;

import com.profect.delivery.domain.cart.service.CartService;
import com.profect.delivery.domain.cart.dto.AddCartDto;
import com.profect.delivery.domain.cart.dto.CartInfoDto;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.global.entity.Cart;

import com.profect.delivery.global.entity.CartItem;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.exception.custom.BusinessErrorCode;
import com.profect.delivery.global.exception.custom.OrderErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

//-------------모든 요청 url : userId와 카트 userId가 같은지 확인하기---------------


    // 카트 조회 GET 요청
    @GetMapping()
    public ResponseEntity<ApiResponse<CartInfoDto>> getCart(){
        //ControllerAdvice에서 model로 받기
        String userId = "U000000011";


        log.info("userId={}",userId);
        List<Cart> carts = cartService.getCartsWithItemsByUserId(userId);

        CartInfoDto cartInfoDto = cartService.CartToCartInfoDto(carts, userId);

        ApiResponse<CartInfoDto> response = ApiResponse.success(cartInfoDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 카트 생성 POST 요청
    @PostMapping("/{storeId}")
    public ResponseEntity<ApiResponse<Object>> createUser(
            @RequestBody List<AddCartDto> addCartDtoList,
            @PathVariable String storeId
    ) throws Exception {
        //model 전달
        String userId = "U000000011";

        UUID cartId = cartService.saveCart(addCartDtoList, userId, storeId);

        if(cartId == null){
            throw new Exception("카트 저장에 실패하였습니다.");
        }

        log.info("complete add cart cartId={}", cartId);


        return ResponseEntity.ok(ApiResponse.success(cartId));
    }

    // 카트 삭제
    @DeleteMapping("/")
    public ResponseEntity<ApiResponse<Void>> deleteCart(String cartId){
        UUID cartUuid = UUID.fromString(cartId);

        if (cartService.deleteCartByCartId(cartUuid)){
            return new  ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
        }else {
            throw new BusinessException(OrderErrorCode.NOT_FOUND_CART);
//            ErrorResponse errorResponse = new ErrorResponse(
//                    2000, "존재하지 않는 카트입니다.", "DELETE /cart", LocalDateTime.now());
//
//            return new  ResponseEntity<>(ApiResponse.failure(errorResponse), HttpStatus.OK);
        }

    }

    //카트에 아이템 추가
    @PostMapping("/item/{cartId}")
    public ResponseEntity<ApiResponse<Object>> addItemToCart(
            @PathVariable String cartId,
            List<AddCartDto> addCartList){

        List<CartItem> cartItems = new ArrayList<CartItem>();
        for  (AddCartDto addCartDto : addCartList){
            CartItem cartItem = new CartItem();
            cartItem.setCart(new Cart(UUID.fromString(cartId)));
            cartItem.setQuantity(Integer.parseInt(addCartDto.getQuantity()));
            cartItem.setMenuId(UUID.fromString(addCartDto.getMenuId()));
            cartItems.add(cartItem);
        }

        List<CartItem> cartItemList = cartService.saveCartItems(cartItems);
        return  new ResponseEntity<>(ApiResponse.success(cartItemList), HttpStatus.OK);
//        if(cartItemList == null){
//            return new  ResponseEntity<>(ApiResponse.fail(), HttpStatus.OK);
//        }else {
//            return new  ResponseEntity<>(ApiResponse.success(cartItemList), HttpStatus.OK);
//        }


    }

    //카트안 아이템 삭제
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable String cartItemId){

        if (cartService.deleteCartItemById(cartItemId)){
            return ResponseEntity.ok(ApiResponse.success());
        }else {
            throw new BusinessException(OrderErrorCode.NOT_FOUND_ITEM);
        }
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> updateCartItem(
            @PathVariable String cartItemId,
            @RequestBody List<AddCartDto> addCartList) {

        String userId = "U000000011"; // TODO: 실제 인증된 사용자 정보로 대체 필요

        if (addCartList == null || addCartList.isEmpty()) {
            throw new BusinessException(BusinessErrorCode.NOT_FOUND);
        }

        boolean updated = cartService.updateCartItem(UUID.fromString(cartItemId), addCartList, userId);

        if (!updated) {
            throw new BusinessException(BusinessErrorCode.FAIL_TO_UPDATE); // 혹은 적절한 에러코드 추가
        }

        return ResponseEntity.ok(ApiResponse.success());
    }


    //아이템 수량 수정
//    @PutMapping("/item/{cartItemId}")
//    public ResponseEntity<ApiResponse<Void>> updateCartItem(
//            @PathVariable String cartItemId,
//            List<AddCartDto> addCartList){
//
//        String userId = "user001";
//        if (addCartList == null || addCartList.isEmpty()){
//            throw new BusinessException(BusinessErrorCode.NOT_FOUND);    }
//
//        if(cartService.updateCartItem(UUID.fromString(cartItemId), addCartList,userId)){
//            return new  ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
//        }else{
//            return new  ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
//        }
//
//    }










}
