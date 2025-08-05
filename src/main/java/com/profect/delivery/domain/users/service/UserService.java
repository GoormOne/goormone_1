package com.profect.delivery.domain.users.service;

import com.profect.delivery.domain.users.dto.UserUpdateRequestDto;
import com.profect.delivery.domain.users.repository.UserRepository;
import com.profect.delivery.global.exception.UserNotFoundException;
import com.profect.delivery.global.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor//생성자 자동 주입
@Slf4j
public class UserService{//비즈니스 로직
    private final UserRepository userRepository;




    @Transactional(readOnly = true)
    public Optional<User> getUserById(String UserId) {
        return userRepository.findByUserId(UserId);
    }


    public void updateUser(UserUpdateRequestDto userUpdateRequestDto, String UserId, String UpdatedBy) {
        User user=userRepository.findByUserId(UserId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.update(userUpdateRequestDto.getName(),
                userUpdateRequestDto.getPassword(),
                userUpdateRequestDto.getEmail(),
                userUpdateRequestDto.getIs_public(),
                UpdatedBy
        );
        userRepository.save(user);
        log.info("Updated user id {}",user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public void createUser(com.profect.delivery.domain.auth.AuthController.SignupRequest request, String encodedPassword) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUserId(java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        user.setUsername(request.username());
        user.setPassword(encodedPassword);
        user.setName(request.name());
        user.setBirth(java.sql.Date.valueOf(request.birth()));
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setPublic(true);
        user.setBanned(false);
        user.setCreatedAt(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        user.setCreatedBy("SYSTEM");
        
        userRepository.save(user);
        log.info("Created new user: {}", user.getUsername());
    }

    @Transactional
    public void softDeleteUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        
        user.setDeletedAt(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        user.setDeletedBy(username);
        
        userRepository.save(user);
        log.info("Soft deleted user: {}", username);
    }

}