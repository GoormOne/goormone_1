package com.profect.delivery.domain.auth;

import com.profect.delivery.domain.auth.dto.TokenInfo;
import com.profect.delivery.domain.users.service.UserService;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.global.entity.User;
import com.profect.delivery.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenInfo>> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for username: {}", request.username());
        
        try {
            User user = userService.findByUsername(request.username());
            log.debug("Found user: {}, stored password hash: {}", user.getUsername(), user.getPassword());
            log.debug("Input password: {}", request.password());
            
            boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());
            log.debug("Password matches: {}", passwordMatches);
            
            if (passwordMatches) {
                String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole());
                String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
                TokenInfo tokenInfo = TokenInfo.of(accessToken, refreshToken, jwtExpiration);
                
                log.info("Login successful for user: {}", user.getUsername());
                return ResponseEntity.ok(ApiResponse.success(tokenInfo));
            }
        } catch (Exception e) {
            log.error("Login error for username: {}, error: {}", request.username(), e.getMessage(), e);
        }
        
        log.warn("Login failed for username: {}", request.username());
        ErrorResponse error = ErrorResponse.of(401, "Invalid credentials", "/auth/login");
        return ResponseEntity.status(401)
            .body(ApiResponse.failure(error));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignupRequest request) {
        log.info("Signup attempt for username: {}", request.username());
        
        try {
            userService.createUser(request, passwordEncoder.encode(request.password()));
            log.info("Signup successful for user: {}", request.username());
            return ResponseEntity.ok(ApiResponse.success("User created successfully"));
        } catch (Exception e) {
            log.error("Signup error for username: {}, error: {}", request.username(), e.getMessage());
            ErrorResponse error = ErrorResponse.of(400, e.getMessage(), "/auth/signup");
            return ResponseEntity.status(400).body(ApiResponse.failure(error));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse<String>> withdraw(Authentication authentication) {
        String username = authentication.getName();
        log.info("Withdraw attempt for username: {}", username);
        
        try {
            userService.softDeleteUser(username);
            log.info("Withdraw successful for user: {}", username);
            return ResponseEntity.ok(ApiResponse.success("User withdrawn successfully"));
        } catch (Exception e) {
            log.error("Withdraw error for username: {}, error: {}", username, e.getMessage());
            ErrorResponse error = ErrorResponse.of(400, e.getMessage(), "/auth/withdraw");
            return ResponseEntity.status(400).body(ApiResponse.failure(error));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfo>> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        
        return ResponseEntity.ok(ApiResponse.success(
            new UserInfo(user.getUsername(), user.getRole(), user.getName(), user.getEmail())
        ));
    }

    public record LoginRequest(String username, String password) {}
    public record SignupRequest(String username, String password, String name, String birth, String email, com.profect.delivery.global.entity.Role role) {}
    public record UserInfo(String username, com.profect.delivery.global.entity.Role role, String name, String email) {}
}