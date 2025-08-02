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


}