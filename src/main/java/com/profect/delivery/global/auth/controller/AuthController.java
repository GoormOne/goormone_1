package com.profect.delivery.global.auth.controller;

import com.profect.delivery.global.auth.dto.AuthRequestDTO;
import com.profect.delivery.global.auth.dto.AuthResponseDTO;
import com.profect.delivery.global.auth.dto.LoginResponseDTO;
import com.profect.delivery.global.auth.service.AuthService;
import com.profect.delivery.global.auth.userdetails.CustomUserDetails;
import com.profect.delivery.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<ApiResponse<AuthResponseDTO.AuthRegisterResponseDTO>> registerCustomer(
            @Valid @RequestBody AuthRequestDTO.RegisterRequestDto request) {
        
        AuthResponseDTO.AuthRegisterResponseDTO response = authService.registerCustomer(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/register/owner")
    public ResponseEntity<ApiResponse<AuthResponseDTO.AuthRegisterResponseDTO>> registerOwner(
            @Valid @RequestBody AuthRequestDTO.RegisterRequestDto request) {
        
        AuthResponseDTO.AuthRegisterResponseDTO response = authService.registerOwner(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody AuthRequestDTO.LoginRequestDto request) {
        
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AuthRequestDTO.PasswordChangeDto request) {
        
        authService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("비밀번호가 성공적으로 변경되었습니다."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(
            @RequestHeader("Authorization") String refreshToken) {
        
        String token = refreshToken.startsWith("Bearer ") ? refreshToken.substring(7) : refreshToken;
        LoginResponseDTO response = authService.refreshToken(token);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}