package com.profect.delivery.domain.store.controller;


import com.profect.delivery.domain.store.dto.request.RegionAddressDto;
import com.profect.delivery.domain.store.dto.request.RegionListAddressDto;
import com.profect.delivery.domain.store.dto.response.RegionDto;
import com.profect.delivery.domain.store.dto.response.RegionListDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.global.DTO.ErrorResponse;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.service.StoreService;
import com.profect.delivery.global.DTO.ApiResponse;
import com.profect.delivery.global.entity.Store;
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
            @RequestBody @Valid final StoreRegisterDto storeRegisterDto) {

        Store savedStore = storeService.saveStore(storeRegisterDto);

        ApiResponse<StoreRegisterDto> response;

        if (savedStore != null) {
            System.out.println("매장을 등록하였습니다");
            response = ApiResponse.success(storeRegisterDto);

        } else {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("매장 정보 등록에 실패하였습니다.");
            response = ApiResponse.failure(error);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> deleteStore(
            @PathVariable("storeId") String storeId) {
        //storeService.deleteStore(storeId);
        boolean success = storeService.deleteStore(storeId);
        ApiResponse<String> response;
        if (success) {
            System.out.println(storeId + "  매장을 삭제하였습니다");
            response = ApiResponse.success(storeId);
        } else {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("매장 삭제에 실패하였습니다.");
            response = ApiResponse.failure(error);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreDto>> getStore(
            @PathVariable String storeId
    ) {
        StoreDto storeDto = storeService.findStoreById(storeId);
        ApiResponse<StoreDto> response;

        if (storeId != null) {
            response = ApiResponse.success(storeDto);
        } else {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("해당 매장 정보가 없습니다.");
            response = ApiResponse.failure(error);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<RegionListDto>> getStoreRegions(
            @PathVariable String storeId
    ) {
        try {
            RegionListDto regionListDto = storeService.getRegions(storeId);
            ApiResponse<RegionListDto> response = ApiResponse.success(regionListDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.NOT_FOUND.value());
            error.setMessage(e.getMessage());
            ApiResponse<RegionListDto> response = ApiResponse.failure(error);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<List<UUID>>> registerStoreRegions(
            @PathVariable String storeId,
            @RequestBody final RegionListAddressDto regionListAddressDto) {
        try {
            System.out.println("Request Dto: " + regionListAddressDto);
            System.out.println("Regions: " + regionListAddressDto.getRegionListAddressDto());

            List<UUID> registerRegionIds = storeService.registerRegions(storeId, regionListAddressDto);
            ApiResponse<List<UUID>> response = ApiResponse.success(registerRegionIds);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.NOT_FOUND.value());
            error.setMessage(e.getMessage());
            ApiResponse<List<UUID>> response = ApiResponse.failure(error);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping("/{storeId}/regions")
    public ResponseEntity<ApiResponse<List<UUID>>> deleteStoreRegions(
            @PathVariable String storeId,
            @RequestBody final RegionListAddressDto regionListAddressDto

    ) {
        try {
            List<UUID> deleteRegionIds = storeService.deleteRegion(storeId, regionListAddressDto);
            ApiResponse<List<UUID>> response = ApiResponse.success(deleteRegionIds);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.NOT_FOUND.value());
            error.setMessage(e.getMessage());
            ApiResponse<List<UUID>> response = ApiResponse.failure(error);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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




