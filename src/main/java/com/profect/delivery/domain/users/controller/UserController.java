package com.profect.delivery.domain.users.controller;

import com.profect.delivery.domain.users.dto.UserAddressesResponseDto;
import com.profect.delivery.domain.users.dto.UserPatchResponseDto;
import com.profect.delivery.domain.users.dto.UserPatchReuestDto;
import com.profect.delivery.domain.users.dto.UserResponseDto;
import com.profect.delivery.domain.users.repository.UserRepository;
import com.profect.delivery.domain.users.service.UserAddressService;
import com.profect.delivery.domain.users.service.UserService;
import com.profect.delivery.global.DTO.ApiResponse;
import com.profect.delivery.global.DTO.ErrorResponse;
import com.profect.delivery.global.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
    @RequestMapping("/users")//post 일 경우 requset body를 dto 에서 유효성 검사를 해야함 ,@Vaild사용해서
    @RequiredArgsConstructor
    public class UserController { //조회 하고 싶은 내용
        @Autowired
        UserService userService;
        @Autowired
        UserAddressService userAddressService;
        @GetMapping
        public ResponseEntity<ApiResponse<UserResponseDto>> getUser() {
            boolean succese = true;
            ApiResponse<UserResponseDto> response;//

            if (succese){
                response = ApiResponse.success(userService.getUserById("user002")
                        .map(UserResponseDto::fromEntity).orElse(null));
            }else {
                ErrorResponse error = new ErrorResponse();
                error.setCode(202);
                error.setMessage("유저 정보가 없습니다.");
                response = ApiResponse.failure(error);
            }
//

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }




//        @PatchMapping
//        public ResponseEntity<UserPatchResponseDto> PatchUser(@RequestBody UserPatchReuestDto userPatchReuestDto) {
//            String id= "ksm3255";
//            return  new UserPatchResponseDto();}

        @GetMapping("/addresses")
        public ResponseEntity<ApiResponse<List<UserAddressesResponseDto>>> getUserAddresses() {
            boolean succese = true;
            ApiResponse<List<UserAddressesResponseDto>> response;//

            if (succese){
                response = ApiResponse.success(userAddressService.findByUserId("user001").stream()
                        .map(UserAddressesResponseDto::fromEntity).collect(Collectors.toList()));
            }else {
                ErrorResponse error = new ErrorResponse();
                error.setCode(202);
                error.setMessage("유저 주소 정보가 없습니다.");
                response = ApiResponse.failure(error);
            }
//

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }


    }
      //get post patch delete 안에 있음



