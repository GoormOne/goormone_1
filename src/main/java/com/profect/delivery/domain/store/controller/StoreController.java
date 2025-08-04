package com.profect.delivery.domain.store.controller;

import com.profect.delivery.domain.store.dto.request.RegionAddressDto;
import com.profect.delivery.domain.store.dto.request.RegionListAddressDto;
import com.profect.delivery.domain.store.dto.response.RegionDto;
import com.profect.delivery.domain.store.dto.response.RegionListDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.domain.store.dto.response.StoreSearchDto;
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

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;


    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerStore(
            String userId ,
            @RequestBody @Valid StoreRegisterDto storeRegisterDto

    ) {
        //더미데이터
         userId = "U000000004";
        storeService.registerStore(userId, storeRegisterDto);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> deleteStore(
            @PathVariable("storeId") String storeId,
            String userId
    ) {

        storeService.deleteStore(userId, storeId);
        return ResponseEntity.ok().body(ApiResponse.success());

    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreDto>> getStore(
            @PathVariable String storeId,
            HttpServletRequest request
    ) {

            storeService.findStoreById(storeId);
            return ResponseEntity.ok(ApiResponse.success());

    }

    @GetMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<RegionListDto>> getStoreRegions(
            @PathVariable String storeId
    ) {
        RegionListDto regions = storeService.getRegions(storeId);
        return ResponseEntity.ok(ApiResponse.success(regions));
    }



    @PostMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<List<UUID>>> registerStoreRegions(
            @PathVariable String storeId,
            @RequestBody final RegionListAddressDto regionListAddressDto
    ) {
        List<UUID> ids = storeService.registerRegions(storeId, regionListAddressDto);
        return ResponseEntity.ok(ApiResponse.success(ids));
    }

    @DeleteMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<List<UUID>>> deleteStoreRegions(
            @PathVariable String storeId,
            @RequestBody final RegionListAddressDto regionListAddressDto
    ) {
        List<UUID> deletedIds = storeService.deleteRegion(storeId, regionListAddressDto);
        return ResponseEntity.ok(ApiResponse.success(deletedIds));
    }

    @GetMapping("/search/categoryName")
    public ResponseEntity<ApiResponse<List<StoreSearchDto>>> searchStoreByKeyword(
            @RequestParam(defaultValue = "") String categoryName
    ) {
        List<StoreSearchDto> result = storeService.searchStoreByKeyword(categoryName);
        return ResponseEntity.ok().body(ApiResponse.success(result));
    }
//    @GetMapping("/search/categoryName")
//    public ResponseEntity<ApiResponse<List<StoreSearchDto>>> searchStore(
//            @RequestParam(defaultValue = "") String categoryName,
//            HttpServletRequest request
//    ) {
//        try {
//            List<StoreSearchDto> stores = storeService.searchStoreByKeyword(categoryName);
//            return ResponseEntity.ok(ApiResponse.success(stores));
//        } catch (RuntimeException e) {
//            ErrorResponse err = ErrorResponse.of(
//                    HttpStatus.NOT_FOUND.value(),
//                    e.getMessage(),
//                    request.getRequestURI());
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(ApiResponse.failure(err));
//        }
    }




//@GetMapping("/search/categoryName")
//public ResponseEntity<ApiResponse<List<StoreSearchDto>>> searchStoreByKeyword(
//        @RequestParam(defaultValue = "") String categoryName
//) {
//    List<StoreSearchDto> result = storeService.searchStoreByKeyword(categoryName);
//    return ResponseEntity.ok().body(ApiResponse.success(result));
//}




