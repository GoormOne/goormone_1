package com.profect.delivery.domain.menu.controller;

import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.domain.menu.dto.request.CreateMenuRequest;
import com.profect.delivery.domain.menu.dto.response.CreateMenuResponse;
import com.profect.delivery.domain.menu.dto.request.UpdateMenuRequest;
import com.profect.delivery.domain.menu.dto.response.UpdateMenuResponse;
import com.profect.delivery.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

//    @PostMapping
//    public ResponseEntity<Menu> createMenu(
//            @RequestBody CreateMenuRequest dto,
//            @RequestHeader("username") String username) {
//
//        Menu saved = menuService.createMenu(dto, username);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(saved);
//    }

    // 성공
//    @PostMapping
//    public ResponseEntity<ApiResponse<CreateMenuResponse>> createMenu(
//            @RequestBody CreateMenuRequest dto,
//            @RequestHeader("username") String username) {
//
//        Menu saved =  menuService.createMenu(dto, username);
//
//        // 성공
//        ApiResponse<CreateMenuResponse> body = ApiResponse.success(CreateMenuResponse.from(saved));
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(body);
//    }

//    // @RestControllerAdvice 없이 Controller에서 실패 분기
//    @PostMapping
//    public ResponseEntity<ApiResponse<CreateMenuResponse>> createMenu(
//            @RequestBody CreateMenuRequest dto,
//            @RequestHeader("username") String username,
//            HttpServletRequest request) {
//
//        // 입력 받은 메뉴 명이 없는 경우
//        if (dto.getMenuName() == null || dto.getMenuName().isBlank()) {
//            ErrorResponse err = new ErrorResponse(
//                    601,
//                    "No Menu Name Entered",
//                    request.getRequestURI(),
//                    LocalDateTime.now().toString()
//            );
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.failure(err));
//        }
//
//        // 서비스 호출 후 결과에 따라 분기
//        try {
//            Menu saved =  menuService.createMenu(dto, username);
//            ApiResponse<CreateMenuResponse> body =  ApiResponse.success(CreateMenuResponse.from(saved));
//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .body(body);
//        } catch (Exception e) {
//            ErrorResponse err = new ErrorResponse(
//                    999,
//                    "Unidentified Error",
//                    request.getRequestURI(),
//                    LocalDateTime.now().toString()
//            );
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.failure(err));
//        }
//    }

    // 수동 예외 분기 제거 + GlobalExceptionalHandler 활용
    @PostMapping
    public ResponseEntity<ApiResponse<CreateMenuResponse>> createMenu(
            @PathVariable UUID storeId,
            @Valid @RequestBody CreateMenuRequest dto,
            @RequestHeader("username") String username) {

        CreateMenuResponse res = menuService.createMenu(storeId, dto, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(res));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<ApiResponse<UpdateMenuResponse>> updateMenu(
            @PathVariable UUID storeId,
            @PathVariable UUID menuId,
            @Valid @RequestBody UpdateMenuRequest dto,
            @RequestHeader("username") String username) {

        UpdateMenuResponse res = menuService.updateMenu(storeId, menuId, dto, username);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable UUID storeId,
            @PathVariable UUID menuId,
            @RequestHeader("username") String username) {

        menuService.deleteMenu(storeId, menuId, username);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}