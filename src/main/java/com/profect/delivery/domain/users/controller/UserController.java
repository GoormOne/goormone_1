package com.profect.delivery.domain.users.controller;

import com.profect.delivery.domain.users.dto.*;
import com.profect.delivery.domain.users.service.UserAddressService;
import com.profect.delivery.domain.users.service.UserService;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.global.advice.GlobalExceptionHandler.*;
import com.profect.delivery.global.advice.UserAddressNotFoundException;
import com.profect.delivery.global.advice.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


    @RestController//컨트롤러 클래스에 붙이는,어노테이션 [rest api 컨트롤러]라는 뜻
    @RequestMapping("/users")//공통 url prefix 설정
    @RequiredArgsConstructor//자동 생성자,final 필드에 자동으로 스피링빈 객체 주입해줌
    public class UserController {
        private final UserService userService;
        private final UserAddressService userAddressService;



        @GetMapping
        public ResponseEntity<ApiResponse<UserResponseDto>> getUser() {
            String currentUserId = "user002";//인증인가 가져오면 유저 id 추출

            return userService.getUserById(currentUserId)
                    .map(UserResponseDto::fromEntity) // Entity를 DTO로 변환
                    .map(ApiResponse::success)        // 성공 응답으로 래핑
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK)) // ResponseEntity 반환
                    .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다.")); // 사용자 없을 시 예외 발생
        }


    @PatchMapping
        public ResponseEntity<ApiResponse<?>> patchUser(
                @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

            String currentUserId = "user0089";//인증인가에서 가져온 유저 id
            String updateby="admin";//인증인가에서 가져온 업데이트한 사람

            userService.updateUser(userUpdateRequestDto,currentUserId,updateby);

            return  new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
        }




        @GetMapping("/addresses")
        public ResponseEntity<ApiResponse<List<UserAddressesResponseDto>>> getUserAddresses() {
            String currentUserId = "user001";

            List<UserAddressesResponseDto> addresses = userAddressService.findByUserId(currentUserId).stream()
                    .map(UserAddressesResponseDto::fromEntity) // 각 엔티티를 DTO로 변환
                    .collect(Collectors.toList()); //
            if (addresses.isEmpty()) {
                throw new UserAddressNotFoundException("등록된 주소 정보를 찾을 수 없습니다.");
            }
            return new ResponseEntity<>(ApiResponse.success(addresses), HttpStatus.OK);
        }

        @PostMapping("/addresses")
        public ResponseEntity<ApiResponse<?>> postUserAddressses(
                @RequestBody UserAddressesRequestDto userAddressesRequestDto) {

            String currentUserId = "user001";

            userAddressService.CreateUserAddress(userAddressesRequestDto,currentUserId);

            return  new ResponseEntity<>(ApiResponse.success(null), HttpStatus.CREATED);
        }



}
      //get post patch delete 안에 있음



