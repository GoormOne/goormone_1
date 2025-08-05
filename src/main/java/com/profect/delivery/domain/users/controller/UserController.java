package com.profect.delivery.domain.users.controller;

import com.profect.delivery.domain.users.dto.*;
import com.profect.delivery.domain.users.service.UserAddressService;
import com.profect.delivery.domain.users.service.UserService;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.exception.UserAddressNotFoundException;
import com.profect.delivery.global.exception.UserNotFoundException;
import com.profect.delivery.global.exception.custom.UserErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


    @RestController//컨트롤러 클래스에 붙이는,어노테이션 [rest api 컨트롤러]라는 뜻
    @RequestMapping("/users")//공통 url prefix 설정
    @RequiredArgsConstructor//자동 생성자,final 필드에 자동으로 스피링빈 객체 주입해줌
    public class UserController {
        private final UserService userService;
        private final UserAddressService userAddressService;



        @GetMapping
        public ResponseEntity<ApiResponse<UserResponseDto>> getUser(HttpServletRequest request) {
            String currentUserId = request.getParameter("userId");;//인증인가 가져오면 유저 id 추출
            UserResponseDto userDto = userService.getUserById(currentUserId);
            return  new ResponseEntity<>(ApiResponse.success(userDto), HttpStatus.OK);
        }


        @PatchMapping
        public ResponseEntity<ApiResponse<?>> patchUser(HttpServletRequest request,
               @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
            String currentUserId = request.getParameter("userId");;//인증인가에서 가져온 유저 id
            String updateby="user";//인증인가에서 가져온 업데이트한 사람
            userService.updateUser(userUpdateRequestDto,currentUserId,updateby);
            return  new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
        }




        @GetMapping("/addresses")
        public ResponseEntity<ApiResponse<List<UserAddressesResponseDto>>> getUserAddresses(HttpServletRequest request) {
            String currentUserId = request.getParameter("userId");
            List<UserAddressesResponseDto> addressesDto = userAddressService.findByUserId(currentUserId);
            return new ResponseEntity<>(ApiResponse.success(addressesDto), HttpStatus.OK);
        }

        @PostMapping("/addresses")
        public ResponseEntity<ApiResponse<?>> postUserAddressses(HttpServletRequest request,
                @RequestBody UserAddressesRequestDto userAddressesRequestDto) {
            String currentUserId = request.getParameter("userId");
            userAddressService.CreateUserAddress(userAddressesRequestDto,currentUserId);
            return  new ResponseEntity<>(ApiResponse.success(null), HttpStatus.CREATED);
        }



}
      //get post patch delete 안에 있음



