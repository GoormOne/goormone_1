package com.profect.delivery.domain.store.controller;


import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.global.DTO.ErrorResponse;
import com.profect.delivery.domain.store.dto.request.RegionDto;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.service.StoreService;
import com.profect.delivery.global.DTO.ApiResponse;
import com.profect.delivery.global.entity.Store;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    //private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ApiResponse<StoreRegisterDto>> registerStore(
            @RequestBody @Valid final StoreRegisterDto storeRegisterDto) {

        Store savedStore  = storeService.saveStore(storeRegisterDto);

        ApiResponse<StoreRegisterDto> response;

        if (savedStore != null) {
            response = ApiResponse.success(storeRegisterDto);
        } else {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("매장 정보 등록에 실패하였습니다.");
            response = ApiResponse.failure(error);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @DeleteMapping("/{storeId}")
//    public ResponseEntity<ApiResponse<String>> deleteStore(
//            @PathVariable String storeId ) {
//        //storeService.deleteStore(storeId);
//        boolean success = false;
//        ApiResponse<String> response;
//        if (success) {
//            response = ApiResponse.success(storeId);
//        } else {
//            ErrorResponse error = new ErrorResponse();
//            response = ApiResponse.failure(error);
//        }
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreDto>> getStore(
            @PathVariable String storeId
    ){
        StoreDto storeDto = storeService.findStoreById(storeId);
        ApiResponse<StoreDto> response ;

        if(storeId != null){
            response = ApiResponse.success(storeDto);
        }else {
            ErrorResponse error = new ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("해당 매장 정보가 없습니다.");
            response = ApiResponse.failure(error);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    @GetMapping("/{storeId}/regions")
//    public ResponseEntity<ResponseDto<Object[]>> getStoreRegions(
//            @PathVariable String storeId
//    ){
//        return ResponseEntity.ok().body(ResponseDto.success());
//    }
//
//    @PostMapping("/{storeId}/regions")
//    public ResponseEntity<ResponseDto<Void>> registerStoreRegion(
//            @PathVariable String storeId ,
//            @RequestBody final RegionDto regionId ) {
//        //storeService.registerRegions(storeId, regionId.regionIds());
//        return ResponseEntity.ok().body(ResponseDto.success());
//    }
//
//    @DeleteMapping("/{storeId}/regions")
//    public ResponseEntity<ResponseDto<Void>> deleteStoreRegion(
//            @PathVariable String storeId ,
//            @RequestBody final RegionDto regionId
//    ){
//        return ResponseEntity.ok().body(ResponseDto.success());
//    }
//
//    @GetMapping("/search/categoryName")
//    public ResponseEntity<ResponseDto<Object>> searchStore(
//        @RequestParam(defaultValue = "") String categoryName
//    )
//    {
//        return ResponseEntity.ok().body(ResponseDto.success());
//    }
}
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




