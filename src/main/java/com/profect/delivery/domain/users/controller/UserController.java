package com.profect.delivery.domain.users.controller;

import com.profect.delivery.domain.users.dto.request.LoginRequestDto;
import com.profect.delivery.domain.users.dto.request.SignupRequestDto;
import com.profect.delivery.domain.users.dto.request.UserAddressesRequestDto;
import com.profect.delivery.domain.users.dto.request.UserUpdateRequestDto;
import com.profect.delivery.domain.users.dto.response.UserAddressesResponseDto;
import com.profect.delivery.domain.users.dto.response.UserResponseDto;
import com.profect.delivery.global.dto.TokenInfo;
import com.profect.delivery.domain.users.dto.*;
import com.profect.delivery.domain.users.service.UserAddressService;
import com.profect.delivery.domain.users.service.UserService;
import com.profect.delivery.global.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAddressService userAddressService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignupRequestDto request) {
        String result = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse<String>> withdraw(Authentication authentication) {
        String username = authentication.getName();
        String result = userService.softDeleteUser(username);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenInfo>> login(@RequestBody LoginRequestDto request) {
        TokenInfo tokenInfo = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(tokenInfo));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoDto>> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        UserInfoDto userInfo = userService.getCurrentUser(username);
        return ResponseEntity.ok(ApiResponse.success(userInfo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(HttpServletRequest request) {
        String currentUserId = request.getParameter("userId");
        UserResponseDto userDto = userService.getUserById(currentUserId);
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> patchUser(HttpServletRequest request,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        String currentUserId = request.getParameter("userId");
        String updateBy = "user";
        userService.updateUser(userUpdateRequestDto, currentUserId, updateBy);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/addresses")
    public ResponseEntity<ApiResponse<List<UserAddressesResponseDto>>> getUserAddresses(HttpServletRequest request) {
        String currentUserId = request.getParameter("userId");
        List<UserAddressesResponseDto> addressesDto = userAddressService.findByUserId(currentUserId);
        return ResponseEntity.ok(ApiResponse.success(addressesDto));
    }

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<Void>> postUserAddresses(HttpServletRequest request,
            @RequestBody UserAddressesRequestDto userAddressesRequestDto) {
        String currentUserId = request.getParameter("userId");
        userAddressService.CreateUserAddress(userAddressesRequestDto, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
    }
}



