package com.profect.delivery.domain.store.controller;

import com.profect.delivery.domain.store.dto.request.RegionAddressDto;
import com.profect.delivery.domain.store.dto.request.RegionListAddressDto;
import com.profect.delivery.domain.store.dto.response.RegionDto;
import com.profect.delivery.domain.store.dto.response.RegionListDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.service.StoreService;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.entity.Store;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    //private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ApiResponse<StoreRegisterDto>> registerStore(
            @RequestBody @Valid final StoreRegisterDto dto,
            HttpServletRequest request
    ) {
        try {
            Store saved = storeService.saveStore(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(dto));
        } catch (RuntimeException e) {   // 중복 등 비즈니스 예외
            ErrorResponse err = ErrorResponse.of(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(err));
        }
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> deleteStore(
            @PathVariable("storeId") String storeId,
            HttpServletRequest request
    ) {
        if (storeService.deleteStore(storeId)) {
            return ResponseEntity.ok(ApiResponse.success(storeId));
        }
        ErrorResponse err = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "매장 삭제에 실패하였습니다.",
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(err));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreDto>> getStore(
            @PathVariable String storeId,
            HttpServletRequest request
    ) {
        StoreDto dto = storeService.findStoreById(storeId);
        if (dto != null) {
            return ResponseEntity.ok(ApiResponse.success(dto));
        }
        ErrorResponse err = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                "해당 매장 정보가 없습니다.",
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(err));
    }

    @GetMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<RegionListDto>> getStoreRegions(
            @PathVariable String storeId,
            HttpServletRequest request
    ) {
        try {
            RegionListDto regions = storeService.getRegions(storeId);
            return ResponseEntity.ok(ApiResponse.success(regions));
        } catch (RuntimeException e) {
            ErrorResponse err = ErrorResponse.of(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.failure(err));
        }
    }


    @PostMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<List<UUID>>> registerStoreRegions(
            @PathVariable String storeId,
            @RequestBody final RegionListAddressDto regionListAddressDto,
            HttpServletRequest request
    ) {
        try {
            List<UUID> ids = storeService.registerRegions(storeId, regionListAddressDto);
            return ResponseEntity.ok(ApiResponse.success(ids));
        } catch (RuntimeException e) {
            ErrorResponse err = ErrorResponse.of(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.failure(err));
        }
    }


    @DeleteMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<List<UUID>>> deleteStoreRegions(
            @PathVariable String storeId,
            @RequestBody final RegionListAddressDto regionListAddressDto,
            HttpServletRequest request
    ) {
        try {
            List<UUID> ids = storeService.deleteRegion(storeId, regionListAddressDto);
            return ResponseEntity.ok(ApiResponse.success(ids));
        } catch (RuntimeException e) {
            ErrorResponse err = ErrorResponse.of(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.failure(err));
        }

    }
}

//
//    @GetMapping("/search/categoryName")
//    public ResponseEntity<ResponseDto<Object>> searchStore(
//        @RequestParam(defaultValue = "") String categoryName
//    )
//    {
//        return ResponseEntity.ok().body(ResponseDto.success());
//    }

//    public class UserController {
//        private final UserService userService;
//
//        @GetMapping("/{info}")
//        public ResponseEntity<User> getUser(@PathVariable Long id) {
//            return userService.getUserById(id)
//                    .map(ResponseEntity::ok) //응답형태
//                    .orElse(ResponseEntity.notFound().build());
//        }
//
//    }



